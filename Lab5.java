package Lab5;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.MyCollection;
import Lab5.InputStaff.ConsoleInputMaster;
import Lab5.jsonStaff.GsonMaster;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 * main class of the project <br>
 * just start it..
 */
public class Lab5 {
    private static final String variableName = "JAVA_PROJECT";

    public static void main(String... args) throws IOException {
        MyCollection<City> myCollection = new MyCollection<>(setUp());
        ConsoleInputMaster<City> consoleInputMaster = new ConsoleInputMaster<>(new Scanner(System.in), myCollection);
        consoleInputMaster.run();
    }

    /**
     * Is invoked in the start of the program and get the initial information about the collection from file.
     * @return the initial state of the collection
     */
    private static LinkedList<City> setUp(){
        LinkedList<City> cities = new GsonMaster().deserialize(variableName);
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
