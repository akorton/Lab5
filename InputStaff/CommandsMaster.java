package Lab5.InputStaff;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.MyCollection;
import Lab5.jsonStaff.GsonMaster;

import java.io.*;
import java.util.*;

/**
 * class that contains all commands
 * @param <T>
 */
public class CommandsMaster<T extends City> {
    private final Map<String, Executable<T>> commands = new HashMap<>();
    private final String DefaultPathName = File.separator.equals("/") ? "Files/OutputFile" : "src\\Lab5\\Files\\OutputFile";

    public CommandsMaster() {
        initializeCommands();
    }

    /**
     * returns class of the command by the name of the command
     * @param name name of the command
     * @return class that implements Executable interface and has method execute
     */
    public Executable<T> getCommandByName(String name) {
        return commands.get(name);
    }

    /**
     * method that ensures that command has received proper number of arguments of they are needed
     * @param number_of_args number of arguments command needs
     * @param args arguments that command received
     * @return arguments if they are correct or null else
     */
    private String[] checkArgs(int number_of_args, String... args){
        if (args.length == number_of_args) {
            return args;
        } else{
            if (args.length > number_of_args){
                System.out.println("Too many arguments for this command.");
                return null;
            } else{
                System.out.println("Not enough arguments for this command.");
                return null;
            }
        }
    }

    /**
     * method that checks if the collection contains the element with given id
     * @param myCollection collection of elements to search in
     * @param id the id of the element
     * @return element if it is in collection or null else
     */
    private T checkIdInCollection(MyCollection<T> myCollection, String id){
        if (!Validator.validateLong(id)) {System.out.println("Wrong argument format."); return null;}
        else{
            Long argLong = Long.parseLong(id);
            T city = myCollection.containsId(argLong);
            if (city == null) {System.out.println("No such id in collection."); return null;}
            return city;
        }
    }

    /**
     * method that initializes all the commands
     */
    private void initializeCommands(){
        commands.put("help", (consoleInputMaster, myCollection, args) -> System.out.println("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_last : удалить последний элемент из коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "reorder : отсортировать коллекцию в порядке, обратном нынешнему\n" +
                "group_counting_by_area : сгруппировать элементы коллекции по значению поля area, вывести количество элементов в каждой группе\n" +
                "filter_greater_than_meters_above_sea_level metersAboveSeaLevel : вывести элементы, значение поля metersAboveSeaLevel которых больше заданного\n" +
                "print_descending : вывести элементы коллекции в порядке убывания"));
        commands.put("exit", (consoleInputMaster, myCollection, args) -> consoleInputMaster.exit());
        commands.put("show", (consoleInputMaster, myCollection, args) -> System.out.println(myCollection));
        commands.put("info", (consoleInputMaster, myCollection, args) -> System.out.println("Creation time: " + myCollection.getCreationTime() + "\n" +
                "Collection type: " + myCollection.getType() + "\n" +
                "Size: " + myCollection.getSize() + "\n" +
                "First element:\n" + myCollection.getFirstElement() + "\n" +
                "Last element:\n" + myCollection.getLastElement()));
        commands.put("add", (inputMaster, myCollection, args)->{
            T city = inputMaster.inputCity();
            if (city != null) {
                myCollection.addLast(city);
            }
            System.out.println("Successfully added.");
        });
        commands.put("update", (InputMaster, myCollection, s)->{
            String[] args = checkArgs(1, s);
            if (args == null) {return;}
            String arg = args[0];
            T city = checkIdInCollection(myCollection, arg);
            if (city == null) {return;}
            T newCity = InputMaster.inputCity(city);
            if (newCity != null){
                System.out.println("Successfully updated.");
            }
        });
        commands.put("remove_by_id", (InputMaster, myCollection, s)->{
            String[] args = checkArgs(1, s);
            if (args == null) {return;}
            String arg = args[0];
            T city = checkIdInCollection(myCollection, arg);
            if (city == null) {return;}
            myCollection.removeCity(city);
            System.out.println("Successfully removed.");
        });
        commands.put("clear", (InputMaster, myCollection, args) -> {
            myCollection.clearCollection();
            System.out.println("Collection was successfully cleared.");
        });
        commands.put("save", (InputMaster, myCollection, s)->{
            try{
                String[] args = checkArgs(0, s);
                if (args == null) return;
                File file = new File(DefaultPathName);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
                String data = new GsonMaster().serialize(myCollection.getMyCollection());
                if (data == null) return;
                outputStreamWriter.write(data);
                outputStreamWriter.close();
                System.out.println("Successfully saved.");
            } catch (FileNotFoundException e){
                System.out.println("Can not create files in the directory.");
            } catch (IOException e){
                System.out.println("Can not write in this file.");
            }
        });
        commands.put("execute_script", (InputMaster, myCollection, s)->{
            String[] args = checkArgs(1, s);
            if (args != null) {
                String arg = args[0];
                FileInputMaster<T> fileInputMaster = new FileInputMaster<>(InputMaster, myCollection, arg);
                fileInputMaster.run();
            }
        });
        commands.put("remove_last", (InputMaster, myCollection, args) -> System.out.println(myCollection.removeLast() ? "Last element was removed." : "There is no last element - collection is clear."));
        commands.put("remove_greater", (InputMaster, myCollection, args)->{
            City city = InputMaster.inputCity();
            if (city == null) return;
            ArrayList<T> citiesToRemove = new ArrayList<>();
            for (T city1: myCollection.getMyCollection()){
                if (city1.compareTo(city) > 0) citiesToRemove.add(city1);
            }
            myCollection.removeAll(citiesToRemove);
            System.out.println("Successfully removed.");
        });
        commands.put("reorder", (InputMaster, myCollection, args)->myCollection.reorder());
        commands.put("group_counting_by_area", (InputMaster, myCollection, args)->{
            Map<Double, Integer> groups = new HashMap<>();
            if (myCollection.getMyCollection().size() == 0) {
                System.out.println("No elements in collection.");
                return;
            }
            for (City city: myCollection.getMyCollection()){
                double cur_area = city.getArea();
                if (groups.containsKey(cur_area)){
                    groups.put(cur_area, groups.get(cur_area)+1);
                } else{
                    groups.put(cur_area, 1);
                }
            }
            for (Map.Entry<Double, Integer> entry: groups.entrySet()){
                Double key = entry.getKey();
                Integer value = entry.getValue();
                System.out.printf("Elements with area %f: %d.\n", key, value);
            }
        });
        commands.put("filter_greater_than_meters_above_sea_level", (consoleInputMaster, myCollection, s)->{
            String[] args = checkArgs(1,s);
            if (args == null) return;
            String arg = args[0];
            if (!Validator.validateFloatValue(arg)) {System.out.println("Wrong argument format."); return;}
            float sea_level = Float.parseFloat(arg);
            int cnt = 0;
            for (City city: myCollection.getMyCollection()){
                if (city.getMetersAboveSeaLevel() > sea_level){
                    System.out.println(city);
                    cnt++;
                }
            }
            if (cnt == 0) System.out.println("No such elements.");
        });
        commands.put("print_descending", (consoleInputMaster, myCollection, args)->{
            LinkedList<T> cur = myCollection.getMyCollection();
            if (cur.size() == 0) System.out.println("No elements in collection.");
            cur.sort(Comparator.reverseOrder());
            for (City city: cur){
                System.out.println(city);
            }
        });
    }

}
