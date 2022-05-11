package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;
import Lab5.Server.MyCollection;

import java.sql.SQLException;
import java.util.LinkedList;

public class RemoveByIdCommand extends CommandOne<Long>{

    public RemoveByIdCommand(MyCollection collection, User user, Long arg){
        super(collection, user, arg);
    }

    public Message<String, ?> execute() throws SQLException, UnauthorizedException {
        checkUnauthorized();
        int userId = DatabaseMaster.getDatabaseMaster().getIdByUser(user);
        if (!DatabaseMaster.getDatabaseMaster().containsId(arg)){
            Message<String, ?> message = new Message<>("No such id in collection.");
            message.setResult(false);
            return message;
        }
        City city = DatabaseMaster.getDatabaseMaster().getCityById(arg);
        if (!(city.getUserId() == userId)){
            Message<String, ?> message = new Message<>("This element is not yours.");
            message.setResult(false);
            return message;
        }
        if (!DatabaseMaster.getDatabaseMaster().removeCityById(arg)){
            throw new SQLException();
        }
        collection.removeCityById(arg);
        Message<String, ?> message = new Message<>("Successfully removed.");
        message.setResult(true);
        return message;
    }
}
