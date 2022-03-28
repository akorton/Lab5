package Lab5.Server;

import Lab5.CommonStaff.JsonStaff.GsonMaster;
import Lab5.Server.Commands.CommandsMaster;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ServerMaster {
    private static final String variableName = "JAVA_PROJECT";
    private static final MyCollection<City> myCollection = new MyCollection<>(setUp());
    private static final CommandsMaster<City> commandsMaster = new CommandsMaster<>(myCollection);
    private static final int buffSize = 30000;

    public static void main(String[] args){
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
            SocketAddress address = new InetSocketAddress(3451);
            DatagramChannel channel = DatagramChannel.open();
//            channel.configureBlocking(false);
            channel.bind(address);
            while (true) {
                byte[] message = new byte[buffSize];
                ByteBuffer buffer = ByteBuffer.wrap(message);
                buffer.clear();
                address = channel.receive(buffer);
                byte[] answer = commandsMaster.executeCommand(message).getBytes(StandardCharsets.UTF_8);
                ByteBuffer bufferAnswer = ByteBuffer.wrap(answer);
                bufferAnswer.clear();
                channel.send(bufferAnswer, address);
            }
        } catch (SocketException e){
            System.out.println("Socket exception.");
        } catch (IOException e){
            System.out.println("IOException.");
        }
    }
}
