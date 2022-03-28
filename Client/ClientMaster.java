package Lab5.Client;


import Lab5.CommonStaff.JsonStaff.GsonMaster;
import Lab5.CommonStaff.Message;
import Lab5.Server.City;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientMaster {
    private static InetSocketAddress socketAddress;
    private static final int port = 3451;

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
        GsonMaster<Message> gsonMaster = new GsonMaster<>();
        byte[] mes = gsonMaster.serialize(message).getBytes(StandardCharsets.UTF_8);
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(mes, mes.length, socketAddress);
            datagramSocket.send(datagramPacket);
            byte[] ans = new byte[2048];
            DatagramPacket answer = new DatagramPacket(ans, ans.length);
            datagramSocket.receive(answer);
            String answerString = new String(ans);
            return answerString.substring(0, answerString.indexOf(0));
        } catch (SocketException e){
            System.out.println("Server issues.");
        } catch (IOException e){
            System.out.println("IOException.");
        }
        return "No answer was received :(";
    }
}
