package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;
import Lab5.Server.MyCollection;

import java.sql.SQLException;
import java.util.LinkedList;

public class ClearCommand extends CommandZero{

    public ClearCommand(MyCollection collection, User user){
        super(collection, user);
    }

    public Message<String, ?> execute() throws SQLException, UnauthorizedException {
        checkUnauthorized();
        LinkedList<City> dbCollection = DatabaseMaster.getDatabaseMaster().getCollectionTable();
        int id = DatabaseMaster.getDatabaseMaster().getIdByUser(user);
        LinkedList<City> citiesToRemove = new LinkedList<>();
        for (City city: dbCollection){
            if (city.getUserId() == id){
                citiesToRemove.add(city);
            }
        }
        if (DatabaseMaster.getDatabaseMaster().removeAll(citiesToRemove)) {
            collection.removeAll(citiesToRemove);
            Message<String, ?> message = new Message<>("Collection was successfully cleared.");
            message.setResult(true);
            return message;
        }
        throw new SQLException();
    }
}
