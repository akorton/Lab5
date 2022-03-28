package Lab5.Client;


import Lab5.CommonStaff.JsonStaff.GsonMaster;
import Lab5.CommonStaff.Message;
import Lab5.Server.City;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientMaster {
    private static InetSocketAddress socketAddress;
    private static final int port = 3451;
    private static final int buffSize = 32000;

    public static void main(String[] args){
        try {
            socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        } catch (UnknownHostException e){
            System.out.println("Unknown host.");
        }
        ConsoleInputMaster<City> consoleInputMaster = new ConsoleInputMaster<>(new Scanner(System.in));
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
