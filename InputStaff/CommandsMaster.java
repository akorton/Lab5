package Lab5.InputStaff;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.MyCollection;
import Lab5.jsonStaff.GsonMaster;

import java.io.*;
import java.util.*;

public class CommandsMaster<T extends City> {
    private final Map<String, ConsoleExecutable<T>> commands = new HashMap<>();
    private final Map<String, FileExecutable<T>> fileCommands = new HashMap<>();
    private final String DefaultPathName = File.separator.equals("/") ? "Files/OutputFile" : "src\\Lab5\\Files\\OutputFile";

    public CommandsMaster() {
        initializeCommands();
        initializeFileCommands();
    }

    public ConsoleExecutable<T> getCommandByName(String name) {
        return commands.get(name);
    }

    public FileExecutable<T> getFileCommandByName(String name){
        return fileCommands.get(name);
    }

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

    private T checkIdInCollection(MyCollection<T> myCollection, String id){
        if (!Validator.validateLong(id)) {System.out.println("Wrong argument format."); return null;}
        else{
            Long argLong = Long.parseLong(id);
            T city = myCollection.containsId(argLong);
            if (city == null) {System.out.println("No such id in collection."); return null;}
            return city;
        }
    }

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
        commands.put("add", (consoleInputMaster, myCollection, args)->{
            myCollection.addLast(consoleInputMaster.inputCity());
            System.out.println("Successfully added.");
        });
        commands.put("update", (consoleInputMaster, myCollection, s)->{
            String[] args = checkArgs(1, s);
            if (args == null) {return;}
            String arg = args[0];
            T city = checkIdInCollection(myCollection, arg);
            if (city == null) {return;}
            T newCity = consoleInputMaster.inputCity(new City(city.getId()));
            myCollection.set(myCollection.indexOf(city), newCity);
            System.out.println("Successfully updated.");
        });
        commands.put("remove_by_id", (consoleInputMaster, myCollection, s)->{
            String[] args = checkArgs(1, s);
            if (args == null) {return;}
            String arg = args[0];
            T city = checkIdInCollection(myCollection, arg);
            if (city == null) {return;}
            myCollection.removeCity(city);
            System.out.println("Successfully removed.");
        });
        commands.put("clear", (consoleInputMaster, myCollection, args) -> {
            myCollection.clearCollection();
            System.out.println("Collection was successfully cleared.");
        });
        commands.put("save", (consoleInputMaster, myCollection, s)->{
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
        commands.put("execute_script", (consoleInputMaster, myCollection, s)->{
            String[] args = checkArgs(1, s);
            if (args != null) {
                String arg = args[0];
                FileInputMaster<T> fileInputMaster = new FileInputMaster<T>(consoleInputMaster, myCollection, arg);
                fileInputMaster.executeScript();
            }
        });
        commands.put("remove_last", (consoleInputMaster, myCollection, args) -> System.out.println(myCollection.removeLast() ? "Last element was removed." : "There is no last element - collection is clear."));
        commands.put("remove_greater", (consoleInputMaster, myCollection, args)->{
            City city = consoleInputMaster.inputCity();
            ArrayList<T> citiesToRemove = new ArrayList<>();
            for (T city1: myCollection.getMyCollection()){
                if (city.compareTo(city1) > 0) citiesToRemove.add(city1);
            }
            myCollection.removeAll(citiesToRemove);
            System.out.println("Successfully removed.");
        });
        commands.put("reorder", (consoleInputMaster, myCollection, args)->myCollection.reorder());
        commands.put("group_counting_by_area", (consoleInputMaster, myCollection, args)->{
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
            cur.sort(Comparator.reverseOrder());
            for (City city: cur){
                System.out.println(city);
            }
        });
    }

    private void initializeFileCommands(){
        fileCommands.put("add", (fileInputMaster, myCollection, args)->{
            T city = fileInputMaster.inputCity();
            if (city != null) {
                myCollection.addLast(city);
            }
        });
        fileCommands.put("update", ((fileInputMaster, myCollection, s) -> {
            String[] args = checkArgs(1, s);
            if (args == null) {return;}
            String arg = args[0];
            T city = checkIdInCollection(myCollection, arg);
            if (city == null) {return;}
            T newCity = fileInputMaster.inputCity(new City(city.getId()));
            if (newCity != null){
                myCollection.set(myCollection.indexOf(city), newCity);
            }
        }));
        fileCommands.put("remove_greater", (((fileInputMaster, myCollection, args) ->{
            T city = fileInputMaster.inputCity();
            if (city == null) return;
            ArrayList<T> citiesToRemove = new ArrayList<>();
            for (T city1: myCollection.getMyCollection()){
                if (city.compareTo(city1) > 0) citiesToRemove.add(city1);
            }
            myCollection.removeAll(citiesToRemove);
        })));
        fileCommands.put("exit", (((fileInputMaster, myCollection, args) -> {
            fileInputMaster.exit(true);
            commands.get("exit").execute(fileInputMaster.getConsoleInputMaster(), myCollection);
        })));
        for (String key: commands.keySet()){
            if (!fileCommands.containsKey(key)){
                fileCommands.put(key, (fileInputMaster, myCollection, args) -> commands.get(key).execute(fileInputMaster.getConsoleInputMaster(), myCollection, args));
            }
        }
    }

}
