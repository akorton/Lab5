package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;
import Lab5.Server.MyCollection;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveGreaterCommand  extends CommandOne<City> {

    public RemoveGreaterCommand(MyCollection collection, User user, City arg){
        super(collection, user, arg);
    }

    public Message<String, ?> execute() throws SQLException, UnauthorizedException {
        checkUnauthorized();
        int userId = DatabaseMaster.getDatabaseMaster().getIdByUser(user);
        LinkedList<City> dbCollection = DatabaseMaster.getDatabaseMaster().getCollectionTable();
        LinkedList<City> citiesToRemove = new LinkedList<>();
        for (City city: dbCollection){
            if (city.getUserId() == userId && city.compareTo(arg) > 0) citiesToRemove.add(city);
        }
        if (citiesToRemove.size() == 0) {
            Message<String, ?> message = new Message<>("No elements to remove.");
            message.setResult(false);
            return message;
        }
        if (!DatabaseMaster.getDatabaseMaster().removeAll(citiesToRemove)) throw new SQLException();
        collection.removeAll(citiesToRemove);
        Message<String, ?> message = new Message<>("Successfully removed.");
        message.setResult(true);
        return message;
    }
}
