package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;
import Lab5.Server.MyCollection;

import java.sql.SQLException;
import java.util.LinkedList;

public class RemoveLastCommand extends CommandZero{

    public RemoveLastCommand(MyCollection collection, User user){
        super(collection, user);
    }

    public Message<String, ?> execute() throws SQLException, UnauthorizedException {
        checkUnauthorized();
        int userId = DatabaseMaster.getDatabaseMaster().getIdByUser(user);
        LinkedList<City> dbCollection = DatabaseMaster.getDatabaseMaster().getCollectionTable();
        if (dbCollection.size() == 0){
            Message<String, ?> message = new Message<>("The collection is empty.");
            message.setResult(false);
            return message;
        }
        City lastCity = dbCollection.getLast();
        if (!(userId == lastCity.getUserId())){
            Message<String, ?> message = new Message<>("This element does not belong to you.");
            message.setResult(false);
            return message;
        }
        if (!DatabaseMaster.getDatabaseMaster().removeCityById(lastCity.getId())){
            throw new SQLException();
        }
        collection.removeLast();
        Message<String, ?> message = new Message<>("Last element was removed.");
        message.setResult(true);
        return message;
    }
}
