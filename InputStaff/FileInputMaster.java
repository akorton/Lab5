package Lab5.InputStaff;

import Lab5.CollectionStaff.*;
import Lab5.CollectionStaff.MyCollection;

import java.io.*;
import java.time.ZonedDateTime;

public class FileInputMaster<T extends City> {
    private ConsoleInputMaster<T> consoleInputMaster;
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

    public FileInputMaster(ConsoleInputMaster<T> consoleInputMaster, MyCollection<T> myCollection, String path) {
        this.consoleInputMaster = consoleInputMaster;
        myCommands = new CommandsMaster<T>();
        this.myCollection = myCollection;
        this.path = path;
    }

    public void executeScript() throws RecursionInFileException {
        try {
            File file = new File(path);
            InputStreamReader reader = new FileReader(path);
            inputStreamReader = reader;
            if (consoleInputMaster.getFilesStack().contains(file.getAbsolutePath())){
                consoleInputMaster.getFilesStack().clear();
                throw new RecursionInFileException();
            }
            consoleInputMaster.getFilesStack().add(file.getAbsolutePath());
            String curCmd = inputLine();
            while (!curCmd.isEmpty() && isRunning){
                executeCmd(curCmd);
                curCmd = inputLine();
            }
            reader.close();
            consoleInputMaster.getFilesStack().remove(file.getAbsolutePath());
            System.out.println("Script was executed.");
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        } catch (IOException e){
            System.out.println("File can not be read from or smth else.");
        }
    }

    private void executeCmd(String cur_str) throws RecursionInFileException {
        try {
            String[] curLine = cur_str.split(" ");
            String curCmd = curLine[0];
            FileExecutable<T> curFileExecutable = myCommands.getFileCommandByName(curCmd);
            String[] args = new String[curLine.length - 1];
            for (int i = 0; i < curLine.length - 1; i++) {
                args[i] = curLine[i + 1];
            }
            curFileExecutable.execute(this, myCollection, args);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Command not found.");
        }
    }

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

    public T inputCity() {
        return inputCity(new City());
    }

    public T inputCity(City city){
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

    public ConsoleInputMaster<T> getConsoleInputMaster(){
        return consoleInputMaster;
    }

    public void exit(boolean exitInputMasterAlso){
        isRunning = false;
        if (consoleInputMaster != null && exitInputMasterAlso) consoleInputMaster.exit();
    }

    public void exit(){
        exit(true);
    }
}
