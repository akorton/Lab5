package Lab5.Server.Commands;


import Lab5.CommonStaff.Others.CommandTypes;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

import java.io.*;
import java.util.logging.Logger;

/**
 * class that contains all commands
 */
public class CommandsMaster<T extends City> {
    private final MyCollection<T> collection;
    private Logger logger;

    public CommandsMaster(MyCollection<T> collection){
        this.collection = collection;
    }

    public String executeCommand(byte[] bytes) throws RecursionInFileException {
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Message<?, ?> message = (Message) objectIn.readObject();
            return executeCommand(message);
        } catch (IOException e){
            return "Unable to parse from bytes.";
        } catch (ClassNotFoundException e){
            return "Class not found exception.";
        }
    }


    public String executeCommand(Message message) throws RecursionInFileException {
        CommandTypes type = message.getType();
        if (logger != null) logger.info("Command: " + type + " is being executed.");
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
                return new ExecuteScriptCommand<>(collection, (String) message.getArg()).execute();
            default:
                return "Unknown command.";
        }
    }

    public void setLogger(Logger logger){
        this.logger = logger;
    }
}
