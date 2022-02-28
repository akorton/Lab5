package Lab5;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.MyCollection;
import Lab5.InputStaff.ConsoleInputMaster;
import Lab5.jsonStaff.GsonMaster;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

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
        return cities != null ? cities : new LinkedList<>();
    }
}
