package Lab5.Server.Commands;


import Lab5.CommonStaff.Others.CommandTypes;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

import java.io.*;
import java.util.logging.Logger;

/**
 * class that contains all commands
 */
public class CommandsMaster {
    private static final MyCollection collection = MyCollection.getCollection();
    private Logger logger;
    private static final CommandsMaster commandsMaster = new CommandsMaster();

    private CommandsMaster(){
    }

    public static CommandsMaster getCommandsMaster(){
        return commandsMaster;
    }

    public Message<String, ?> executeCommand(byte[] bytes) throws RecursionInFileException {
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Message<?, ?> message = (Message<?, ?>) objectIn.readObject();
            return executeCommand(message);
        } catch (IOException e){
            Message<String, ?> mes = new Message<>("Unable to parse from bytes.");
            mes.setResult(false);
            return mes;
        } catch (ClassNotFoundException e){
            Message<String, ?> mes = new Message<>("Class not found exception.");
            mes.setResult(false);
            return mes;
        }
    }


    public Message<String, ?> executeCommand(Message<?, ?> message) throws RecursionInFileException {
        CommandTypes type = message.getType();
        Message<String, ?> incorrectCity = new Message<>("Invalid City. Try again.");
        incorrectCity.setResult(false);
        if (logger != null) logger.info("Command: " + type + " is being executed.");
        switch (type) {
            case HELP:
                return new HelpCommand(collection).execute();
            case INFO:
                return new InfoCommand(collection).execute();
            case SHOW:
                return new ShowCommand(collection).execute();
            case CLEAR:
                return new ClearCommand(collection).execute();
            case REORDER:
                return new ReorderCommand(collection).execute();
            case REMOVE_LAST:
                return new RemoveLastCommand(collection).execute();
            case PRINT_DESCENDING:
                return new PrintDescendingCommand(collection).execute();
            case GROUP_COUNTING:
                return new GroupCommand(collection).execute();
            case ADD:
                if (City.validateCity((City) message.getArg())) {
                    return new AddCommand(collection, (City) message.getArg()).execute();
                } else return incorrectCity;
            case UPDATE:
                if (City.validateCity((City) message.getArg2())){
                    return new UpdateCommand(collection, (Long) message.getArg(), (City) message.getArg2()).execute();
                } else return incorrectCity;
            case REMOVE_BY_ID:
                return new RemoveByIdCommand(collection, (Long) message.getArg()).execute();
            case FILTER_GREATER:
                return new FilterCommand(collection, (Float) message.getArg()).execute();
            case REMOVE_GREATER:
                if (City.validateCity((City) message.getArg())){
                    return new RemoveGreaterCommand(collection, (City) message.getArg()).execute();
                } else return incorrectCity;
            case EXECUTE_SCRIPT:
                return new ExecuteScriptCommand(collection, (String) message.getArg()).execute();
            case REGISTER:
                return new RegisterCommand((User) message.getArg()).execute();
            default:
                Message<String, ?> message1 = new Message<>("Unknown command.");
                message1.setResult(false);
                return message1;
        }
    }

    public void setLogger(Logger logger){
        this.logger = logger;
    }
}
