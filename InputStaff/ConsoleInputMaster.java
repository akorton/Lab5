package Lab5.InputStaff;

import Lab5.CollectionStaff.*;
import Lab5.CollectionStaff.MyCollection;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInputMaster<T extends City> extends InputMaster<T> {
    private final Scanner scanner;
    private boolean isRunning = true;
    private final CommandsMaster<T> myCommands;
    private final MyCollection<T> myCollection;
    private final ArrayList<String> filesStack = new ArrayList<>();

    public ConsoleInputMaster(Scanner scanner, MyCollection<T> myCollection){
        this.scanner = scanner;
        this.myCommands = new CommandsMaster<>();
        this.myCollection = myCollection;
    }

    public void run(){
        System.out.println("help : вывести справку по доступным командам");
        while (isRunning) {
            System.out.print("Enter command: ");
            executeCmd(scanner.nextLine());
        }
    }

    public T inputCity(City curCity){
        System.out.println("Entering City...");

        String name = validateInput("name", Validator::validateName);
        curCity.setName(name);

        Coordinates coordinates = new Coordinates();
        System.out.println("Entering coordinates...");

        Double X = Double.parseDouble(validateInput("x", Validator::validateX));
        coordinates.setX(X);

        Float Y = Float.parseFloat(validateInput("y", Validator::validateY));
        coordinates.setY(Y);
        curCity.setCoordinates(coordinates);

        double area = Double.parseDouble(validateInput("area", Validator::validateArea));
        curCity.setArea(area);

        Long population = Long.parseLong(validateInput("population", Validator::validatePopulation));
        curCity.setPopulation(population);

        float metersAboveSeaLevel = Float.parseFloat(validateInput("metersAboveSeaLevel", Validator::validateFloatValue));
        curCity.setMetersAboveSeaLevel(metersAboveSeaLevel);

        float agglomeration = Float.parseFloat(validateInput("agglomeration", Validator::validateFloatValue));
        curCity.setAgglomeration(agglomeration);

        Climate climate = Climate.valueOf(validateInput("Climate", Validator::validateClimate, true, Climate.values()));
        curCity.setClimate(climate);

        StandartOfLiving standartOfLiving = StandartOfLiving.valueOf(validateInput("StandartOfLiving", Validator::validateStandartOfLining, true, StandartOfLiving.values()));
        curCity.setStandartOfLiving(standartOfLiving);

        Human governor = new Human();
        int age = Integer.parseInt(validateInput("age", Validator::validateAge));
        governor.setAge(age);

        java.time.LocalDate localBirthday = java.time.LocalDate.parse(validateInput("birthday(format yyyy-MM-dd)", Validator::validateBirthday));
        String birthday = localBirthday + "T00:00:00+03:00[Europe/Moscow]";
        governor.setBirthday(ZonedDateTime.parse(birthday));

        curCity.setGovernor(governor);
        return (T) curCity;
    }

    public T inputCity(){
        return inputCity(new City());
    }

    private String validateInput(String name, Validatable validator, boolean isEnum, Enum[] enumValues){
        String standartOutput;
        if (!isEnum) standartOutput = "Enter " + name + ": ";
        else {
            standartOutput = "Enter " + name +"(";
            for (Enum en: enumValues){
                standartOutput += en.name() + ", ";
            }
            standartOutput = standartOutput.substring(0, standartOutput.length()-2);
            standartOutput += "): ";
        }

        System.out.printf(standartOutput, name);
        String s = scanner.nextLine();
        while (!validator.validate(s)){
            System.out.printf("Incorrect %s\n%s", name, standartOutput);
            s = scanner.nextLine();
        }
        return s;
    }

    private String validateInput(String name, Validatable validator){
        return validateInput(name, validator, false, null);
    }

    private void executeCmd(String cur_str){
        try {
            String[] curLine = cur_str.split(" ");
            String curCmd = curLine[0];
            Executable<T> curConsoleExecutable = myCommands.getCommandByName(curCmd);
            String[] args = new String[curLine.length - 1];
            for (int i = 0; i < curLine.length - 1;i++){
                args[i] = curLine[i+1];
            }
            curConsoleExecutable.execute(this, myCollection, args);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Command not found.");
        } catch (RecursionInFileException e){
            System.out.println("Recursion in file spotted.");
        }
    }

    public ArrayList<String> getFilesStack(){
        return filesStack;
    }

    public void exit(){
        this.isRunning = false;
    }
}
