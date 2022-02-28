package Lab5.InputStaff;

import Lab5.CollectionStaff.*;
import Lab5.CollectionStaff.MyCollection;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * class that operates all the File input processes
 * @param <T>
 */
public class FileInputMaster<T extends City> extends InputMaster<T>{
    private InputMaster<T> inputMaster;
    private CommandsMaster<T> myCommands;
    private InputStreamReader inputStreamReader;
    private MyCollection<T> myCollection;
    private boolean isRunning = true;
    private String path;

    public FileInputMaster(String path){
        this.path = path;
    }

    public FileInputMaster(InputStreamReader inputStreamReader){
        this.inputStreamReader = inputStreamReader;
    }

    public FileInputMaster(InputMaster<T> inputMaster, MyCollection<T> myCollection, String path) {
        this.inputMaster = inputMaster;
        myCommands = new CommandsMaster<T>();
        this.myCollection = myCollection;
        this.path = path;
    }

    /**
     * reads the file and executes all the commands
     * @throws RecursionInFileException when recursion in file appears
     */
    public void run() throws RecursionInFileException {
        try {
            File file = new File(path);
            InputStreamReader reader = new FileReader(path);
            inputStreamReader = reader;
            if (inputMaster.getFilesStack().contains(file.getAbsolutePath())){
                inputMaster.getFilesStack().clear();
                throw new RecursionInFileException();
            }
            inputMaster.getFilesStack().add(file.getAbsolutePath());
            String curCmd = inputLine();
            while (!curCmd.isEmpty() && isRunning){
                executeCmd(curCmd);
                curCmd = inputLine();
            }
            reader.close();
            inputMaster.getFilesStack().remove(file.getAbsolutePath());
            System.out.println("Script was executed.");
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        } catch (IOException e){
            System.out.println("File can not be read from or smth else.");
        }
    }

    /**
     * executes currect command with the arguments if possible
     * @param cur_str string of command name and arguments
     * @throws RecursionInFileException thrown if the recursion in file is spotted
     */
    private void executeCmd(String cur_str) throws RecursionInFileException {
        try {
            String[] curLine = cur_str.split(" ");
            String curCmd = curLine[0];
            Executable<T> curExecutable = myCommands.getCommandByName(curCmd);
            String[] args = new String[curLine.length - 1];
            for (int i = 0; i < curLine.length - 1; i++) {
                args[i] = curLine[i + 1];
            }
            curExecutable.execute(this, myCollection, args);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Command not found.");
        }
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
            if (file.createNewFile()){
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
     * method that returns the current stack of files, program is executing
     * @return ArrayList of files names
     */
    public ArrayList<String> getFilesStack(){
        return inputMaster.getFilesStack();
    }

    /**
     * stops the loop in run
     * @param exitInputMasterAlso if true invokes exit method in the inputMaster also
     */
    public void exit(boolean exitInputMasterAlso){
        isRunning = false;
        if (inputMaster != null && exitInputMasterAlso) inputMaster.exit();
    }

    /**
     * default representation of exit with exitInputMasterAlso = true
     */
    public void exit(){
        exit(true);
    }
}
