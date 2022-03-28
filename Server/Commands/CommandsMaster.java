package Lab5.Server.Commands;


import Lab5.CommonStaff.CommandTypes;
import Lab5.CommonStaff.Message;
import Lab5.Server.City;
import Lab5.Server.MyCollection;
import Lab5.CommonStaff.JsonStaff.GsonMaster;
import Lab5.Server.RecursionInFileException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * class that contains all commands
 */
public class CommandsMaster<T extends City> {
    private static final String DefaultPathName = File.separator.equals("/") ? "Files/OutputFile" : "src\\Lab5\\Files\\OutputFile";
    private final MyCollection<T> collection;

    public CommandsMaster(MyCollection<T> collection){
        this.collection = collection;
    }

    /**
     * method that initializes all the commands
     */
    private void initializeCommands() {

//        commands.put("save", (InputMaster, myCollection, s)->{
//            try{
//                String[] args = checkArgs(0, s);
//                if (args == null) return;
//                File file = new File(DefaultPathName);
//                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
//                String data = new GsonMaster().serialize(myCollection.getMyCollection());
//                if (data == null) return;
//                outputStreamWriter.write(data);
//                outputStreamWriter.close();
//                System.out.println("Successfully saved.");
//            } catch (FileNotFoundException e){
//                System.out.println("Can not create files in the directory.");
//            } catch (IOException e){
//                System.out.println("Can not write in this file.");
//            }
//        });
    }

    public String executeCommand(byte[] bytes){
//        Message<?, ?> message = new GsonMaster<Message<?, ?>>().deserialize(serializedCommand, new Message<>());
//        CommandTypes type = message.getType();
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Message<?, ?> message = (Message) objectIn.readObject();
            CommandTypes type = message.getType();
            switch (type) {
                case HELP:
                    return new HelpCommand<>(collection).execute();
                case INFO:
                    return new InfoCommand<>(collection).execute();
                case SHOW:
                    return new ShowCommand<>(collection).execute();
                case CLEAR:
                    return new ClearCommand<>(collection).execute();
                case REORDER:
                    return new ReorderCommand<>(collection).execute();
                case REMOVE_LAST:
                    return new RemoveLastCommand<>(collection).execute();
                case PRINT_DESCENDING:
                    return new PrintDescendingCommand<>(collection).execute();
                case GROUP_COUNTING:
                    return new GroupCommand<>(collection).execute();
                case ADD:
                    return new AddCommand<>(collection, (T) message.getArg()).execute();
                case UPDATE:
                    return new UpdateCommand<>(collection, (Long) message.getArg(), (T) message.getArg2()).execute();
                case REMOVE_BY_ID:
                    return new RemoveByIdCommand<>(collection, (Long) message.getArg()).execute();
                case FILTER_GREATER:
                    return new FilterCommand<>(collection, (Float) message.getArg()).execute();
                case REMOVE_GREATER:
                   return new RemoveGreaterCommand<>(collection, (T) message.getArg()).execute();
                case EXECUTE_SCRIPT:
                    try {
                        return new ExecuteScriptCommand<>(collection, (String) message.getArg()).execute();
                    } catch (RecursionInFileException e){
                        return "Recursion in file spotted.";
                    }
                default:
                    return "Unknown command.";
            }
        } catch (IOException e){
            return "Unable to parse from bytes.";
        } catch (ClassNotFoundException e){
            return "Class not found exception.";
        }
    }
}
