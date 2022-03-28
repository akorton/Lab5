package Lab5.Server;

import Lab5.Client.ClientMaster;
import Lab5.CommonStaff.CommandTypes;
import Lab5.CommonStaff.InputMaster;
import Lab5.Client.Validator;
import Lab5.CommonStaff.Message;
import Lab5.CommonStaff.WrongNumberOfArguments;
import Lab5.Server.Commands.CommandsMaster;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.LinkedList;

/**
 * class that operates all the File input processes
 * @param <T>
 */
public class FileInputMaster<T extends City> extends InputMaster<T> {
    private static final LinkedList<String> filesStack = new LinkedList<>();
    private InputStreamReader inputStreamReader;
    private MyCollection<T> myCollection;
    private CommandsMaster<T> commandsMaster;
    private boolean isRunning = true;
    private String path;

    public FileInputMaster(String path){
        this.path = path;
    }

    public FileInputMaster(InputStreamReader inputStreamReader){
        this.inputStreamReader = inputStreamReader;
    }

    public FileInputMaster(MyCollection<T> myCollection, String path) {
        this.myCollection = myCollection;
        this.commandsMaster = new CommandsMaster<>(myCollection);
        this.path = path;
    }

    /**
     * reads the file and executes all the commands
     * @throws RecursionInFileException when recursion in file appears
     */
    public String run() throws RecursionInFileException {
        String result = "";
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
                result += executeCmd(curCmd);
                result += "\n";
                curCmd = inputLine();
            }
            reader.close();
            getFilesStack().remove(file.getAbsolutePath());
            result += "Script was executed.";
            return result;
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
    private String executeCmd(String cur_str) throws RecursionInFileException {
        try {
            String[] curLine = cur_str.split(" ");
            String curCmd = curLine[0];
            switch (curCmd){
                case "help":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.HELP));
                    break;
                case "show":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.SHOW));
                    break;
                case "info":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.INFO));
                    break;
                case "clear":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.CLEAR));
                    break;
                case "remove_last":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.REMOVE_LAST));
                    break;
                case "reorder":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.REORDER));
                    break;
                case "group_counting_by_area":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.GROUP_COUNTING));
                    break;
                case "print_descending":
                    if (validateNumberOfArgs(0, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.PRINT_DESCENDING));
                    break;
                case "remove_by_id":
                    if (validateNumberOfArgs(1, curLine)) {
                        String id = curLine[1];
                        long idLong;
                        try{
                            idLong = Long.parseLong(id);
                        } catch (NumberFormatException e){
                            return "Wrong argument.";
                        }
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.REMOVE_BY_ID, idLong));
                    }
                    break;
                case "execute_script":
                    if (validateNumberOfArgs(1, curLine))
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.EXECUTE_SCRIPT, curLine[1]));
                    break;
                case "filter_greater_than_meters_above_sea_level":
                    if (validateNumberOfArgs(1, curLine)){
                        String meters = curLine[1];
                        float m;
                        try {
                            m = Float.parseFloat(meters);
                        } catch (NumberFormatException e){
                            return "Wrong argument.";
                        }
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.FILTER_GREATER, m));
                    }
                    break;
                case "add":
                    if (validateNumberOfArgs(0, curLine)){
                        City c = inputCity();
                        if (c == null)
                            return "Wrong argument.";
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.ADD, c));
                    }
                    break;
                case "remove_greater":
                    if (validateNumberOfArgs(0, curLine)){
                        City c = inputCity();
                        if (c == null)
                            return "Wrong argument.";
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.REMOVE_GREATER, c));
                    }
                    break;
                case "update_by_id":
                    if (validateNumberOfArgs(1, curLine)){
                        String id = curLine[1];
                        long idLong;
                        try{
                            idLong = Long.parseLong(id);
                        } catch (NumberFormatException e){
                            return "Wrong argument.";
                        }
                        City c = inputCity();
                        if (c == null)
                            return "Wrong argument.";
                        return commandsMaster.executeCommand(new Message<>(CommandTypes.UPDATE, idLong, c));
                    }
                    break;
                case "exit":
                    if (validateNumberOfArgs(0, curLine)){
                        exit();
                        return "Have a good day sir!";
                    }
                    break;
                default:
                    return "Command not found.";
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return "Command not found.";
        } catch (WrongNumberOfArguments e){
            return "Wrong number of arguments.";
        }
        return null;
    }

    /**
     * helps to input a line
     * @return line
     * @throws IOException thrown if it is not possible to read from the file
     */
    private String inputLine() throws IOException{
        String curStr = "";
        int cur = inputStreamReader.read();
        while (cur != -1) {
            char cur_char = (char) cur;
            if (cur_char == Character.LINE_SEPARATOR) {
                inputStreamReader.read();
                break;
            } else {
                curStr += cur_char;
            }
            cur = inputStreamReader.read();
        }
        return curStr;
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
            FileInputMaster<T> fileInputMaster = new FileInputMaster<T>(fileInputStream);
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
    public T inputCity(T city){
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
        return (T) city;
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
