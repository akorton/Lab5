package Lab5.Client;


import Lab5.CommonStaff.Others.CommandTypes;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.PasswordGenerationException;
import Lab5.CommonStaff.Others.User;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientMaster {
    private static InetSocketAddress socketAddress;
    private static int port;
    private static User user;
    static {
        try {
            port = Integer.parseInt(System.getenv("JAVA_PORT"));
        } catch (NumberFormatException e){
            port = 2222;
        }
    }
    private static final int buffSize = 32000;

    public static void main(String[] args){
        try {
            socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        } catch (UnknownHostException e){
            System.out.println("Unknown host.");
        }
        ConsoleInputMaster consoleInputMaster = new ConsoleInputMaster(new Scanner(System.in));
        try {
            consoleInputMaster.run();
        } catch (NullPointerException e){
            System.out.println("You have not been authorized.\nPls try again.");
        }
    }

    public static Message<String, ?> sendInfo(Message<?, ?> message){
        byte[] mes;
        boolean authorizationFl = message.getType().equals(CommandTypes.LOGIN) || message.getType().equals(CommandTypes.REGISTER);
        message.setUser(user);
        try{
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream(buffSize);
            ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
            objectOut.writeObject(message);
            mes = byteOut.toByteArray();
        } catch (IOException e){
            Message<String, ?> answerMes = new Message<>("Unable to serialize.");
            answerMes.setResult(false);
            return answerMes;
        }
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            if (mes.length == 0) {
                Message<String, ?> answerMes = new Message<>("Some problems before transferring to server.");
                answerMes.setResult(false);
                return answerMes;
            }
            DatagramPacket datagramPacket = new DatagramPacket(mes, mes.length, socketAddress);
            datagramSocket.send(datagramPacket);
            byte[] ans = new byte[buffSize];
            DatagramPacket answer = new DatagramPacket(ans, ans.length);
            datagramSocket.setSoTimeout(2000);
            try {
                datagramSocket.receive(answer);
                ByteArrayInputStream byteInput = new ByteArrayInputStream(ans);
                ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                Message<String, ?> answerMes = (Message<String, ?>) objectInput.readObject();
                if (answerMes.getArg().indexOf(0) == -1  && answerMes.getArg().getBytes(StandardCharsets.UTF_8).length > buffSize - 2000) {
                    answerMes.setArg(answerMes.getArg() + "\nWarning!\nThe answer is too large, so it is most likely incomplete!");
                    answerMes.setResult(false);
                    return answerMes;
                }
                if (authorizationFl && answerMes.isResult()){
                    user = (User) answerMes.getArg2();
                    answerMes.setArg(answerMes.getArg() + "\nPrint help to see the list of all available commands.");
                }
                return answerMes;
            } catch (SocketTimeoutException e){
                Message<String, ?> answerMes = new Message<>("Server does not respond.");
                answerMes.setResult(false);
                return answerMes;
            } catch (ClassNotFoundException e){
                Message<String, ?> answerMes =  new Message<>("The answer from server can not be read.");
                answerMes.setResult(false);
                return answerMes;
            }
        } catch (SocketException e){
            Message<String, ?> answerMes = new Message<>("Socket is not acceptable.");
            answerMes.setResult(false);
            return answerMes;
        } catch (IOException e){
            Message<String, ?> answerMes = new Message<>("Cannot understand server's respond.");
            answerMes.setResult(false);
            return answerMes;
        }
    }

    public void setUser(User u){
        user = u;
        System.out.println("Successfully logged in as " + u.getName() + ".");
    }
}
