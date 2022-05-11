package Lab5.Client;

import Lab5.CommonStaff.Others.*;
import Lab5.CommonStaff.CollectionStaff.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * class that operates all the input from console
 */
public class ConsoleInputMaster extends InputMaster {
    private final Scanner scanner;
    private boolean isRunning = true;

    public ConsoleInputMaster(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * overrides the method from InputMaster class
     * the main method that runs the input
     */
    public String run() {
        while (isRunning) {
            System.out.print("Enter command: ");
            try {
                executeCmd(scanner.nextLine());
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Have a good day sir!");
                break;
            }
        }
        return null;
    }

    private User inputUser() throws NoSuchElementException {
        System.out.println("Input login:");
        String name = scanner.nextLine();
        if (!Validator.validateName(name)){
            System.out.println("Login should not be empty.");
            return null;
        }
        System.out.println("Input password:");
        String password = scanner.nextLine();
        if (!Validator.validateName(password)){
            System.out.println("Password should not be empty.");
            return null;
        }
        try {
            return new User(name, password);
        } catch (PasswordGenerationException e){
            return null;
        }
    }

    /**
     * method that inputs City object and returns it
     *
     * @param curCity the City to set all the fields
     * @return T object with all inputted and validated fields
     */
    public City inputCity(City curCity) {
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
        return curCity;
    }

    /**
     * invokes another inputCity method with new City object
     *
     * @return T object
     */
    public City inputCity() {
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
        StringBuilder standartOutput;
        if (!isEnum) standartOutput = new StringBuilder("Enter " + name + ": ");
        else {
            standartOutput = new StringBuilder("Enter " + name + "(");
            for (Enum en : enumValues) {
                standartOutput.append(en.name()).append(", ");
            }
            standartOutput = new StringBuilder(standartOutput.substring(0, standartOutput.length() - 2));
            standartOutput.append("): ");
        }

        System.out.printf(standartOutput.toString(), name);
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

    protected boolean validateNumberOfArgs(int number, String[] line) {
        if (number != line.length - 1) {
            System.out.println("Wrong number of arguments.");
            return false;
        }
        return true;
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
            switch (curCmd) {
                case "help":
                    if (validateNumberOfArgs(0, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.HELP)).getArg());
                    break;
                case "show":
                    if (validateNumberOfArgs(0, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.SHOW)).getArg());
                    break;
                case "info":
                    if (validateNumberOfArgs(0, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.INFO)).getArg());
                    break;
                case "clear":
                    if (validateNumberOfArgs(0, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.CLEAR)).getArg());
                    break;
                case "remove_last":
                    if (validateNumberOfArgs(0, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.REMOVE_LAST)).getArg());
                    break;
                case "reorder":
                    if (validateNumberOfArgs(0, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.REORDER)).getArg());
                    break;
                case "group_counting_by_area":
                    if (validateNumberOfArgs(0, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.GROUP_COUNTING)).getArg());
                    break;
                case "print_descending":
                    if (validateNumberOfArgs(0, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.PRINT_DESCENDING)).getArg());
                    break;
                case "remove_by_id":
                    if (validateNumberOfArgs(1, curLine)) {
                        String id = curLine[1];
                        long idLong;
                        try {
                            idLong = Long.parseLong(id);
                        } catch (NumberFormatException e) {
                            idLong = Long.parseLong(validateInput("id", Validator::validateLong));
                        }
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.REMOVE_BY_ID, idLong)).getArg());
                    }
                    break;
                case "execute_script":
                    if (validateNumberOfArgs(1, curLine))
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.EXECUTE_SCRIPT, curLine[1])).getArg());
                    break;
                case "filter_greater_than_meters_above_sea_level":
                    if (validateNumberOfArgs(1, curLine)) {
                        String meters = curLine[1];
                        float m;
                        try {
                            m = Float.parseFloat(meters);
                        } catch (NumberFormatException e) {
                            m = Float.parseFloat(validateInput("metersAboveSeaLevel", Validator::validateFloatValue));
                        }
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.FILTER_GREATER, m)).getArg());
                    }
                    break;
                case "add":
                    if (validateNumberOfArgs(0, curLine)) {
                        City c = inputCity();
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.ADD, c)).getArg());
                    }
                    break;
                case "remove_greater":
                    if (validateNumberOfArgs(0, curLine)) {
                        City c = inputCity();
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.REMOVE_GREATER, c)).getArg());
                    }
                    break;
                case "update":
                    if (validateNumberOfArgs(1, curLine)) {
                        String id = curLine[1];
                        long idLong;
                        try {
                            idLong = Long.parseLong(id);
                        } catch (NumberFormatException e) {
                            idLong = Long.parseLong(validateInput("id", Validator::validateLong));
                        }
                        City c = inputCity();
                        System.out.println(ClientMaster.sendInfo(new Message<>(CommandTypes.UPDATE, idLong, c)).getArg());
                    }
                    break;
                case "exit":
                    if (validateNumberOfArgs(0, curLine)) {
                        exit();
                        System.out.println("Have a good day sir!");
                    }
                    break;
                case "register":
                    if (validateNumberOfArgs(0, curLine)){
                        User user = inputUser();
                        if (user != null) {
                            Message<User, ?> message = new Message<>(CommandTypes.REGISTER);
                            message.setArg(user);
                            String answer = ClientMaster.sendInfo(message).getArg();
                            System.out.println(answer);
                        }
                    }
                    break;
                case "login":
                    if (validateNumberOfArgs(0, curLine)){
                        User user = inputUser();
                        if (user != null) {
                            Message<User, ?> message = new Message<>(CommandTypes.LOGIN);
                            message.setArg(user);
                            String answer = ClientMaster.sendInfo(message).getArg();
                            System.out.println(answer);
                        }
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
     * method that stops the main loop in run method
     */
    public void exit() {
        this.isRunning = false;
    }
}
