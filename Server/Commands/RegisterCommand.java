package Lab5.Server.Commands;

import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;
import Lab5.Server.MyCollection;

import java.sql.SQLException;
import java.util.Map;

public class RegisterCommand extends CommandOne<User>{

    public RegisterCommand(MyCollection collection, User user, User userArg){
        super(collection, user, userArg);
    }

    public Message<String, ?> execute() throws SQLException, AlreadyAuthorizedException {
        checkAuthorized();
        Map<Integer, User> users = DatabaseMaster.getDatabaseMaster().getIdToUsersTable();
        for (Map.Entry<Integer, User> entry: users.entrySet()){
            if (entry.getValue().getName().equals(arg.getName())){
                Message<String, ?> message = new Message<>("User with that name already exists.");
                message.setResult(false);
                return message;
            }
        }
        if (DatabaseMaster.getDatabaseMaster().insertUser(arg)) {
            String yourUserId = "Your user id: " + DatabaseMaster.getDatabaseMaster().getIdByUser(arg);
            Message<String, ?> message = new Message<>("Registration was successful.\n" + yourUserId);
            message.setResult(true);
            return message;
        }
        Message<String, ?> message = new Message<>("Cannot connect to database.");
        message.setResult(false);
        return message;
    }

}
