package Lab5.Server;

import Lab5.CommonStaff.CollectionStaff.*;
import Lab5.CommonStaff.Others.*;
import Lab5.Server.Commands.CommandsMaster;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.LinkedList;

/**
 * class that operates all the File input processes
 */
public class FileInputMaster extends InputMaster {
    private static final LinkedList<String> filesStack = new LinkedList<>();
    private InputStreamReader inputStreamReader;
    private final CommandsMaster commandsMaster = CommandsMaster.getCommandsMaster();
    private boolean isRunning = true;
    private String path;
    private User user;

    public FileInputMaster(String path){
        this.path = path;
    }

    public FileInputMaster(String path, User user){
        this(path);
        this.user = user;
    }

    public FileInputMaster(InputStreamReader inputStreamReader){
        this.inputStreamReader = inputStreamReader;
    }


    /**
     * reads the file and executes all the commands
     * @throws RecursionInFileException when recursion in file appears
     */
    public String run() throws RecursionInFileException {
        StringBuilder result = new StringBuilder();
        try {
            File file = new File(path);
//            System.out.println(file.getAbsolutePath());
            InputStreamReader reader = new FileReader(path);
            inputStreamReader = reader;
            if (getFilesStack().contains(file.getAbsolutePath())){
                getFilesStack().clear();
                throw new RecursionInFileException();
            }
            getFilesStack().add(file.getAbsolutePath());
            String curCmd = inputLine();
            while (!curCmd.isEmpty() && isRunning){
                result.append(executeCmd(curCmd).getArg());
                result.append("\n");
                curCmd = inputLine();
            }
            reader.close();
            getFilesStack().remove(file.getAbsolutePath());
            result.append("Script was executed.");
            return result.toString();
        } catch (FileNotFoundException e){
            return "File not found.";
        } catch (IOException e){
            return "File can not be read from or smth else.";
        }
    }

    /**
     * executes current command with the arguments if possible
     * @param cur_str string of command name and arguments
     * @throws RecursionInFileException thrown if the recursion in file is spotted
     */
    private Message<String, ?> executeCmd(String cur_str) throws RecursionInFileException {
        try {
            String[] curLine = cur_str.split(" ");
            String curCmd = curLine[0];
            Message<String, ?> wrongArg = new Message<>("Wrong argument.");
            wrongArg.setResult(false);
            switch (curCmd){
                case "help":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.HELP, user));
                    break;
                case "show":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.SHOW, user));
                    break;
                case "info":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.INFO, user));
                    break;
                case "clear":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.CLEAR, user));
                    break;
                case "remove_last":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.REMOVE_LAST, user));
                    break;
                case "reorder":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.REORDER, user));
                    break;
                case "group_counting_by_area":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.GROUP_COUNTING, user));
                    break;
                case "print_descending":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.PRINT_DESCENDING, user));
                    break;
                case "remove_by_id":
                    if (validateNumberOfArgs(1, curLine)) {
                        String id = curLine[1];
                        long idLong;
                        try{
                            idLong = Long.parseLong(id);
                        } catch (NumberFormatException e){
                            return wrongArg;
                        }
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.REMOVE_BY_ID, idLong, user));
                    }
                    break;
                case "execute_script":
                    if (validateNumberOfArgs(1, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.EXECUTE_SCRIPT, curLine[1], user));
                    break;
                case "filter_greater_than_meters_above_sea_level":
                    if (validateNumberOfArgs(1, curLine)){
                        String meters = curLine[1];
                        float m;
                        try {
                            m = Float.parseFloat(meters);
                        } catch (NumberFormatException e){
                            return wrongArg;
                        }
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.FILTER_GREATER, m, user));
                    }
                    break;
                case "add":
                    if (validateNumberOfArgs(0, curLine)){
                        City c = inputCity();
                        if (c == null)
                            return wrongArg;
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.ADD, c, user));
                    }
                    break;
                case "remove_greater":
                    if (validateNumberOfArgs(0, curLine)){
                        City c = inputCity();
                        if (c == null)
                            return wrongArg;
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.REMOVE_GREATER, c, user));
                    }
                    break;
                case "update":
                    if (validateNumberOfArgs(1, curLine)){
                        String id = curLine[1];
                        long idLong;
                        try{
                            idLong = Long.parseLong(id);
                        } catch (NumberFormatException e){
                            return wrongArg;
                        }
                        City c = inputCity();
                        if (c == null)
                            return wrongArg;
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.UPDATE, idLong, c, user));
                    }
                    break;
                case "exit":
                    if (validateNumberOfArgs(0, curLine)){
                        exit();
                        Message<String, ?> message = new Message<>("Have a good day sir!");
                        message.setResult(false);
                        return message;
                    }
                    break;
                default:
                    Message<String, ?> message = new Message<>("Command not found.");
                    message.setResult(false);
                    return message;
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            Message<String, ?> message = new Message<>("Command not found.");
            message.setResult(false);
            return message;
        } catch (WrongNumberOfArguments e){
            Message<String, ?> message = new Message<>("Wrong number of arguments.");
            message.setResult(false);
            return message;
        }
        Message<String, ?> message = new Message<>("Command not found.");
        message.setResult(false);
        return message;
    }

    /**
     * helps to input a line
     * @return line
     * @throws IOException thrown if it is not possible to read from the file
     */
    private String inputLine() throws IOException{
        StringBuilder curStr = new StringBuilder();
        int cur = inputStreamReader.read();
        while (cur != -1) {
            char cur_char = (char) cur;
            if (cur_char == Character.LINE_SEPARATOR) {
                inputStreamReader.read();
                break;
            } else {
                curStr.append(cur_char);
            }
            cur = inputStreamReader.read();
        }
        return curStr.toString();
    }

    /**
     * helps to input all the file (to parse it from json for example)
     * @return file in string representation
     */
    public String inputFile(){
        try{
            File file = new File(path);
            if (!file.exists()){
                System.out.println("The file does not exist.");
                return null;
            }
            InputStreamReader fileInputStream = new InputStreamReader(new FileInputStream(file));
            FileInputMaster fileInputMaster = new FileInputMaster(fileInputStream);
            String result = "";
            String cur_string = fileInputMaster.inputLine();
            while (!cur_string.isEmpty()){
                result = result.concat(cur_string);
                cur_string = fileInputMaster.inputLine();
            }
            return result;
        } catch (IOException e){
            System.out.println("Exception while reading the file.");
            return null;
        } catch (NullPointerException e){
            System.out.println("No path given.");
            return null;
        }
    }

    /**
     * helps to input the city
     * @param city object fields of which would be modified
     * @return T object
     */
    public City inputCity(City city){
        try{
            String name = inputLine();
            if (!Validator.validateName(name)){
                return null;
            }
            city.setName(name);
            Coordinates coordinates = new Coordinates();
            String x = inputLine();
            if (!Validator.validateX(x)){
                return null;
            }
            String y = inputLine();
            if (!Validator.validateY(y)){
                return null;
            }
            coordinates.setX(Double.parseDouble(x));
            coordinates.setY(Float.parseFloat(y));
            city.setCoordinates(coordinates);
            String area = inputLine();
            if (!Validator.validateArea(area)){
                return null;
            }
            city.setArea(Double.parseDouble(area));
            String population = inputLine();
            if (!Validator.validatePopulation(population)){
                return null;
            }
            city.setPopulation(Long.parseLong(population));
            String metersAboveSeaLevel = inputLine();
            if (!Validator.validateFloatValue(metersAboveSeaLevel)){
                return null;
            }
            city.setMetersAboveSeaLevel(Float.parseFloat(metersAboveSeaLevel));
            String agglomeration = inputLine();
            if (!Validator.validateFloatValue(agglomeration)){
                return null;
            }
            city.setAgglomeration(Float.parseFloat(agglomeration));
            String climate = inputLine();
            if (!Validator.validateClimate(climate)){
                return null;
            }
            city.setClimate(Climate.valueOf(climate));
            String standartOFLiving = inputLine();
            if (!Validator.validateStandartOfLining(standartOFLiving)){
                return null;
            }
            city.setStandartOfLiving(StandartOfLiving.valueOf(standartOFLiving));
            Human governor = new Human();
            String age = inputLine();
            if (!Validator.validateAge(age)){
                return null;
            }
            governor.setAge(Integer.parseInt(age));
            String birthday = inputLine();
            if (!Validator.validateBirthday(birthday) && !Validator.validateZonedDateTime(birthday)){
                return null;
            }
            if (Validator.validateZonedDateTime(birthday)){
                governor.setBirthday(ZonedDateTime.parse(birthday));
            } else{
                birthday += "T00:00:00+03:00[Europe/Moscow]";
                java.time.ZonedDateTime birthdayTime = ZonedDateTime.parse(birthday);
                governor.setBirthday(birthdayTime);
            }
            city.setGovernor(governor);
        } catch (IOException e){
            System.out.println("Wrong city input.");
            return null;
        }
        return city;
    }

    /**
     * stops the loop in run
     */
    public void exit(){
        isRunning = false;
        filesStack.pop();
    }

    private static LinkedList<String> getFilesStack(){
        return filesStack;
    }
}
