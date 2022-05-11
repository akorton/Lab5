package Lab5.Server.Commands;


import Lab5.CommonStaff.Others.CommandTypes;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

import java.io.*;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * class that contains all commands
 */
public class CommandsMaster {
    private static final MyCollection collection = MyCollection.getCollection();
    private static Logger logger;
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
        try {
            switch (type) {
                case HELP:
                    return new HelpCommand(collection, message.getUser()).execute();
                case INFO:
                    return new InfoCommand(collection, message.getUser()).execute();
                case SHOW:
                    return new ShowCommand(collection, message.getUser()).execute();
                case CLEAR:
                    return new ClearCommand(collection, message.getUser()).execute();
                case REORDER:
                    return new ReorderCommand(collection, message.getUser()).execute();
                case REMOVE_LAST:
                    return new RemoveLastCommand(collection, message.getUser()).execute();
                case PRINT_DESCENDING:
                    return new PrintDescendingCommand(collection, message.getUser()).execute();
                case GROUP_COUNTING:
                    return new GroupCommand(collection, message.getUser()).execute();
                case ADD:
                    if (City.validateCity((City) message.getArg())) {
                        return new AddCommand(collection, message.getUser(), (City) message.getArg()).execute();
                    } else return incorrectCity;
                case UPDATE:
                    if (City.validateCity((City) message.getArg2())) {
                        return new UpdateCommand(collection, message.getUser(), (Long) message.getArg(), (City) message.getArg2()).execute();
                    } else return incorrectCity;
                case REMOVE_BY_ID:
                    return new RemoveByIdCommand(collection, message.getUser(), (Long) message.getArg()).execute();
                case FILTER_GREATER:
                    return new FilterCommand(collection, message.getUser(), (Float) message.getArg()).execute();
                case REMOVE_GREATER:
                    if (City.validateCity((City) message.getArg())) {
                        return new RemoveGreaterCommand(collection, message.getUser(), (City) message.getArg()).execute();
                    } else return incorrectCity;
                case EXECUTE_SCRIPT:
                    return new ExecuteScriptCommand(collection, message.getUser(), (String) message.getArg()).execute();
                case REGISTER:
                    return new RegisterCommand(collection, message.getUser(), (User) message.getArg()).execute();
                case LOGIN:
                    return new LoginCommand(collection, message.getUser(), (User) message.getArg()).execute();
                default:
                    Message<String, ?> message1 = new Message<>("Unknown command.");
                    message1.setResult(false);
                    return message1;
            }
        } catch (SQLException e){
            e.printStackTrace();
            Message<String, ?> databaseIssue = new Message<>("No connection with database.");
            databaseIssue.setResult(false);
            return databaseIssue;
        } catch (UnauthorizedException e){
            Message<String, ?> message1 = new Message<>("Reminding you that only 'register' and 'login' commands are available while unauthorized.");
            message1.setResult(false);
            return message1;
        } catch (AlreadyAuthorizedException e){
            Message<String, ?> message1 = new Message<>("You have already been authorized.");
            message1.setResult(false);
            return message1;
        }
    }

    public void setLogger(Logger logger){
        CommandsMaster.logger = logger;
    }
}
