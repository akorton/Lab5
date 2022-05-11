package Lab5.Server;

import Lab5.CommonStaff.Others.Message;
import Lab5.Server.Commands.CommandsMaster;
import Lab5.Server.Database.DatabaseMaster;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.*;

public class ServerMaster {
    private static final String variableName = "JAVA_PROJECT";
    private static final MyCollection myCollection = MyCollection.getCollection();
    private static final CommandsMaster commandsMaster = CommandsMaster.getCommandsMaster();
    private static final String LoggerConfig = File.separator.equals("/") ? "Files/LoggerConfig" : "src\\Lab5\\Server\\Files\\LoggerConfig";
    private static final int buffSize = 32000;
    private static Logger logger;
    private static int port;
    static {
        try {
            DatabaseMaster.setUp();
            myCollection.addAll(DatabaseMaster.getDatabaseMaster().getCollectionTable());
        } catch (SQLException e){
            System.out.println("No connection with database.");
        }
        try {
            port = Integer.parseInt(System.getenv("JAVA_PORT"));
        } catch (NumberFormatException e){
            port = 2222;
        }
        try{
            FileInputStream ins = new FileInputStream(LoggerConfig);
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger("Server");
            commandsMaster.setLogger(logger);
        } catch (FileNotFoundException e){
            System.out.println("No logger config file.");
        } catch (IOException e) {
            System.out.println("Probably wrong config for logger.");
        };
    }

    public static void main(String[] args){
        Thread saveHook = new Thread(()->{

        });
        Runtime.getRuntime().addShutdownHook(saveHook);
        start();
    }

    private static void start(){
        logger.info("Server is up.");
        try {
            SocketAddress address = new InetSocketAddress(port);
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(address);
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            while (selector.select() > 0){
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()){
                    SelectionKey sk = it.next();
                    if (sk.isReadable()) {
                        logger.info("New message on server.");
                        //READ
                        byte[] message = new byte[buffSize];
                        ByteBuffer buffer = ByteBuffer.wrap(message);
                        buffer.clear();
                        DatagramChannel sock = (DatagramChannel) sk.channel();
                        SocketAddress curAdr =  sock.receive(buffer);
                        //PROCEED
                        Message<String, ?> answer = new Message<>();
                        try {
                            answer = commandsMaster.executeCommand(message);
                            if (answer.getArg().length() > buffSize){
                                answer.setArg(answer.getArg().substring(0, buffSize - 1000));
                            }
                        } catch (RecursionInFileException e) {
                            answer.setArg("Recursion in file spotted.");
                        }
                        AttachedObject curObj = new AttachedObject(curAdr, answer);
                        sock.configureBlocking(false);
                        SelectionKey newKey = sock.register(sk.selector(), SelectionKey.OP_WRITE);
                        newKey.attach(curObj);
                    }
                    if (sk.isWritable()) {
                        //WRITE
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream(buffSize);
                        ObjectOutputStream objectArray = new ObjectOutputStream(byteArray);
                        objectArray.writeObject(((AttachedObject) sk.attachment()).getAnswer());
                        byte[] answerByte = byteArray.toByteArray();
                        if (answerByte.length > buffSize){
                            byte[] answerByte1 = new byte[buffSize];
                            for (int i = 0; i < buffSize;i++){
                                answerByte1[i] = answerByte[i];
                            }
                            answerByte = answerByte1;
                        }
                        ByteBuffer bufferAnswer = ByteBuffer.wrap(answerByte);
                        bufferAnswer.clear();
                        DatagramChannel sock = (DatagramChannel) sk.channel();
                        sock.send(bufferAnswer, ((AttachedObject) sk.attachment()).getAdr());
                        sock.configureBlocking(false);
                        sock.register(sk.selector(), SelectionKey.OP_READ);
                        logger.info("Answer send back.");
                    }
                }
                it.remove();
            }
        } catch (SocketException e){
            System.out.println("Socket exception.");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("IOException.");
            e.printStackTrace();
        }
    }
}
