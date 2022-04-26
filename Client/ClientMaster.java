package Lab5.Client;


import Lab5.CommonStaff.Others.Message;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMaster {
    private static InetSocketAddress socketAddress;
    private static int port;
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
//            socketAddress = new InetSocketAddress(InetAddress.getByName("s336667@helios.se.ifmo.ru") , port);
        } catch (UnknownHostException e){
            System.out.println("Unknown host.");
        }
        ConsoleInputMaster consoleInputMaster = new ConsoleInputMaster(new Scanner(System.in));
        consoleInputMaster.run();
    }

    public static String sendInfo(Message<?, ?> message){
        byte[] mes;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream(buffSize);
            ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
            objectOut.writeObject(message);
            mes = byteOut.toByteArray();
        } catch (IOException e){
            return "Unable to serialize.";
        }
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            if (mes.length == 0) return "Some problems before transferring to server.";
            DatagramPacket datagramPacket = new DatagramPacket(mes, mes.length, socketAddress);
            datagramSocket.send(datagramPacket);
            byte[] ans = new byte[buffSize];
            DatagramPacket answer = new DatagramPacket(ans, ans.length);
            datagramSocket.setSoTimeout(2000);
            try {
                datagramSocket.receive(answer);

                String answerString = new String(ans);
                return answerString.substring(0, answerString.indexOf(0));
            } catch (SocketTimeoutException e){
                return "Server does not respond.";
            }
        } catch (SocketException e){
            System.out.println("Server issues.");
        } catch (IOException e){
            System.out.println("IOException.");
        }
        return "No answer was received :(";
    }
}
