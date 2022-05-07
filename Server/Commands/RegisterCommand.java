package Lab5.Server.Commands;

import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;

import java.util.Map;

public class RegisterCommand extends CommandOne<User>{
    private User user;


    public RegisterCommand(User user){
        this.user = user;
    }

    public Message<String, ?> execute(){
        Map<Integer, User> users = DatabaseMaster.getDatabaseMaster().getUsersTable();
        if (users == null) {
            Message<String, ?> message = new Message<>("Cannot connect to database.");
            message.setResult(false);
            return message;
        }
        for (Map.Entry<Integer, User> entry: users.entrySet()){
            if (entry.getValue().getName().equals(user.getName())){
                Message<String, ?> message = new Message<>("User with that name already exists.");
                message.setResult(false);
                return message;
            }
        }
        if (DatabaseMaster.getDatabaseMaster().insertUser(user)) {
            Message<String, ?> message = new Message<>("Registration was successful.");
            message.setResult(true);
            return message;
        }
        Message<String, ?> message = new Message<>("Cannot connect to database.");
        message.setResult(false);
        return message;
    }

}
