package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;
import Lab5.Server.MyCollection;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class AddCommand extends CommandOne<City>{

    public AddCommand(MyCollection collection, User user, City arg){
        super(collection, user, arg);
    }

    public Message<String, ?> execute() throws SQLException, UnauthorizedException {
        checkUnauthorized();
        int id = DatabaseMaster.getDatabaseMaster().getIdByUser(user);
        arg.setUserId(id);
        if (DatabaseMaster.getDatabaseMaster().insertCity(arg)) {
            collection.addLast(arg);
            Message<String, ?> message = new Message<>("Successfully added.");
            message.setResult(true);
            return message;
        }
        Message<String, ?> message = new Message<>("Can not add city to database.");
        message.setResult(false);
        return message;
    }
}
