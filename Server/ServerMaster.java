package Lab5.Server;

import Lab5.CommonStaff.JsonStaff.GsonMaster;
import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.Commands.CommandsMaster;
import Lab5.Server.Commands.SaveCommand;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class ServerMaster {
    private static final String variableName = "JAVA_PROJECT";
    private static final MyCollection<City> myCollection = new MyCollection<>(setUp());
    private static final CommandsMaster<City> commandsMaster = new CommandsMaster<>(myCollection);
    private static final String DefaultPathName = File.separator.equals("/") ? "Files/InputFile" : "src\\Lab5\\Server\\Files\\InputFile";
    private static final int buffSize = 30000;
    private static int port;
    static {
        try {
            port = Integer.parseInt(System.getenv("JAVA_PORT"));
        } catch (NumberFormatException e){
            port = 3451;
        }
    }

    public static void main(String[] args){
        Thread saveHook = new Thread(()->{
            SaveCommand<City, String> save = new SaveCommand<>(myCollection, DefaultPathName);
            System.out.println(save.execute());
        });
        Runtime.getRuntime().addShutdownHook(saveHook);
        start();
    }

    /**
     * Is invoked in the start of the program and get the initial information about the collection from file.
     * @return the initial state of the collection
     */
    private static LinkedList<City> setUp(){
        LinkedList<City> cities = new GsonMaster<>().deserialize(variableName);
        if (cities == null) return new LinkedList<>();
        LinkedList<City> citiesToRemove = new LinkedList<>();
        Map<Long, Integer> idToNumberMap = new HashMap<>();
        for (City city: cities){
            if (idToNumberMap.containsKey(city.getId())) idToNumberMap.put(city.getId(), idToNumberMap.get(city.getId()) + 1);
            else idToNumberMap.put(city.getId(), 1);
        }
        for (City city: cities){
            if (!City.validateCity(city) || idToNumberMap.get(city.getId()) > 1){
                citiesToRemove.add(city);
            }
        }
        cities.removeAll(citiesToRemove);
        return cities;
    }

    private static void start(){
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
                    if (sk.isReadable()){
                        byte[] message = new byte[buffSize];
                        ByteBuffer buffer = ByteBuffer.wrap(message);
                        buffer.clear();
                        address = channel.receive(buffer);
                        String stringAnswer;
                        try {
                            stringAnswer = commandsMaster.executeCommand(message);
                        } catch (RecursionInFileException e){
                            stringAnswer = "Recursion in file spotted.";
                        }
                        byte[] answer = stringAnswer.getBytes(StandardCharsets.UTF_8);
                        ByteBuffer bufferAnswer = ByteBuffer.wrap(answer);
                        bufferAnswer.clear();
                        channel.send(bufferAnswer, address);
                    }
                }
                it.remove();
            }
        } catch (SocketException e){
            System.out.println("Socket exception.");
        } catch (IOException e){
            System.out.println("IOException.");
        }

    }
}
