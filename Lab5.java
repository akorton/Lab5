package Lab5;

import Lab5.Client.ClientMaster;
import Lab5.Server.City;
import Lab5.Server.MyCollection;
import Lab5.Client.ConsoleInputMaster;
import Lab5.CommonStaff.JsonStaff.GsonMaster;

import java.io.*;
import java.util.*;

/**
 * main class of the project <br>
 * just start it...<br>
 * brrrrr<br>
 */
public class Lab5 {
    private static final String variableName = "JAVA_PROJECT";

    public static void main(String... args) throws IOException {
        MyCollection<City> myCollection = new MyCollection<>(setUp());
        ClientMaster clientMaster = new ClientMaster(3451);
        clientMaster.start();
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
}
