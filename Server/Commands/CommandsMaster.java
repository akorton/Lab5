package Lab5.Server.Commands;


import Lab5.CommonStaff.CommandTypes;
import Lab5.CommonStaff.Message;
import Lab5.Server.City;
import Lab5.Server.MyCollection;
import Lab5.CommonStaff.JsonStaff.GsonMaster;
import Lab5.Server.RecursionInFileException;

import java.io.*;

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

    public String executeCommand(String serializedCommand){
        Message<?, ?> message = new GsonMaster<Message>().deserialize(serializedCommand, Message.class);
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
                Message<T, ?> message1 = new GsonMaster<Message>().deserialize(serializedCommand, Message.class);
                return new AddCommand<>(collection, message1.getArg()).execute();
            case UPDATE:
                Message<Long, T> message2 = new GsonMaster<Message>().deserialize(serializedCommand, Message.class);
                return new UpdateCommand<>(collection, message2.getArg(), message2.getArg2()).execute();
            case REMOVE_BY_ID:
                Message<Long, ?> message3 = new GsonMaster<Message>().deserialize(serializedCommand, Message.class);
                return new RemoveByIdCommand<>(collection, message3.getArg()).execute();
            case FILTER_GREATER:
                Message<Float, ?> message4 = new GsonMaster<Message>().deserialize(serializedCommand, Message.class);
                return new FilterCommand<>(collection, message4.getArg()).execute();
            case REMOVE_GREATER:
                Message<T, ?> message5 = new GsonMaster<Message>().deserialize(serializedCommand, Message.class);
                return new RemoveGreaterCommand<>(collection, message5.getArg()).execute();
            case EXECUTE_SCRIPT:
                try {
                    Message<String, ?> message6 = new GsonMaster<Message>().deserialize(serializedCommand, Message.class);
                    return new ExecuteScriptCommand<>(collection, message6.getArg()).execute();
                } catch (RecursionInFileException e){
                    return "Recursion in file spotted.";
                }
            default:
                return "Unknown command.";
        }
    }
}
