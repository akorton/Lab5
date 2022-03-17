package Lab5.Client;


import Lab5.Server.City;

import java.net.*;
import java.util.Scanner;

public class ClientMaster {
    InetSocketAddress socketAddress;
    public ClientMaster(Integer port){

        try {
            this.socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        } catch (UnknownHostException e){
            System.out.println("Unknown host.");
        }
    }

    public void start(){
        ConsoleInputMaster<City> consoleInputMaster = new ConsoleInputMaster<>(new Scanner(System.in), this);
        consoleInputMaster.run();
    }

    public void sendInfo(String type, String serializedArg){

    }

    public void sendInfo(String type){

    }
}
