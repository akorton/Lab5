package Lab5.Server.Commands;

import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;
import Lab5.Server.MyCollection;

import java.sql.SQLException;
import java.util.Map;

public class LoginCommand extends CommandOne<User>{

    public LoginCommand(MyCollection collection, User user, User userArg){
        super(collection, user, userArg);
    }

    public Message<String, ?> execute() throws SQLException, AlreadyAuthorizedException {
        checkAuthorized();
        Map<Integer, User> users = DatabaseMaster.getDatabaseMaster().getIdToUsersTable();
        for (Map.Entry<Integer, User> entry: users.entrySet()){
            arg.setSalt(entry.getValue().getSalt());
            arg.encodePassword();
            if (arg.equals(entry.getValue())){
                String yourUserId = "Your user id: " + entry.getKey();
                Message<String, ?> message = new Message<>("Successfully logged in.\n" + yourUserId);
                message.setResult(true);
                return message;
            } else if (arg.getName().equals(entry.getValue().getName())){
                System.out.println();
                Message<String, ?> message = new Message<>("Incorrect password.");
                message.setResult(false);
                return message;
            }
        }
        Message<String, ?> message = new Message<>("No such username.");
        message.setResult(false);
        return message;
    }
}
