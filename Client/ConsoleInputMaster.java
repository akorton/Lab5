package Lab5.Client;

import Lab5.CommonStaff.CommandTypes;
import Lab5.CommonStaff.JsonStaff.GsonMaster;
import Lab5.Server.*;
import Lab5.Server.MyCollection;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * class that operates all the input from console
 *
 * @param <T>
 */
public class ConsoleInputMaster<T extends City> extends InputMaster<T> {
    private final Scanner scanner;
    private final ClientMaster clientMaster;
    private boolean isRunning = true;
    private final ArrayList<String> filesStack = new ArrayList<>();

    public ConsoleInputMaster(Scanner scanner, ClientMaster clientMaster) {
        this.scanner = scanner;
        this.clientMaster = clientMaster;
    }

    /**
     * overrides the method from InputMaster class
     * the main method that runs the input
     */
    public void run() {
        System.out.println("help : вывести справку по доступным командам");
        while (isRunning) {
            System.out.print("Enter command: ");
            try {
                executeCmd(scanner.nextLine());
            } catch (NoSuchElementException | IllegalStateException e){
                System.out.println("Not this time.");
            }
        }
    }

    /**
     * method that inputs City object and returns it
     *
     * @param curCity the City to set all the fields
     * @return T object with all inputted and validated fields
     */
    public T inputCity(City curCity) {
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

    /**
     * invokes another inputCity method with new City object
     *
     * @return T object
     */
    public T inputCity() {
        return inputCity(new City());
    }

    /**
     * method that validates the given input String using the given validating function
     *
     * @param name       name of the field
     * @param validator  validating function
     * @param isEnum     true if the field is Enum value false otherwise
     * @param enumValues null if the field is enum else contains the values of the Enum
     * @return value of the field converted to String
     */
    private String validateInput(String name, Validatable validator, boolean isEnum, Enum[] enumValues) {
        String standartOutput;
        if (!isEnum) standartOutput = "Enter " + name + ": ";
        else {
            standartOutput = "Enter " + name + "(";
            for (Enum en : enumValues) {
                standartOutput += en.name() + ", ";
            }
            standartOutput = standartOutput.substring(0, standartOutput.length() - 2);
            standartOutput += "): ";
        }

        System.out.printf(standartOutput, name);
        String s = scanner.nextLine();
        while (!validator.validate(s)) {
            System.out.printf("Incorrect %s\n%s", name, standartOutput);
            s = scanner.nextLine();
        }
        return s;
    }

    /**
     * basic validate Input function if the field is not enum
     *
     * @param name      name of the field
     * @param validator function to validate String
     * @return field converted to String
     */
    private String validateInput(String name, Validatable validator) {
        return validateInput(name, validator, false, null);
    }

    /**
     * method that executes given command
     *
     * @param cur_str string that contains command name and its arguments separated with the space character
     */
    private void executeCmd(String cur_str) {
        try {
            String[] curLine = cur_str.split(" ");
            String curCmd = curLine[0];
            switch (curCmd){
                case "help":
                    if (curLine.length != 1){
                        System.out.println("Неверное число аргументов.");
                    } else{
                        GsonMaster<CommandTypes> gsonMaster = new GsonMaster<>();
                        String type = gsonMaster.serialize(CommandTypes.HELP);
                        clientMaster.sendInfo(type);
                    }
                    break;
                default:
                    System.out.println("Command not found.");
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Command not found.");
        }
    }

    /**
     * method that returns the current stack of files, program is executing
     *
     * @return ArrayList of files names
     */
    public ArrayList<String> getFilesStack() {
        return filesStack;
    }

    /**
     * method that stops the main loop in run method
     */
    public void exit() {
        this.isRunning = false;
    }
}
