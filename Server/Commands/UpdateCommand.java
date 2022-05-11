package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.Database.DatabaseMaster;
import Lab5.Server.MyCollection;

import java.sql.SQLException;
import java.util.Objects;

public class UpdateCommand extends CommandTwo<Long, City>{

    public UpdateCommand(MyCollection collection, User user, Long arg1, City arg2){
        super(collection, user, arg1, arg2);
    }

    public Message<String, ?> execute() throws SQLException, UnauthorizedException {
        checkUnauthorized();
        if (!DatabaseMaster.getDatabaseMaster().containsId(arg)){
            Message<String, ?> message = new Message<>("No such id in collection.");
            message.setResult(false);
            return message;
        }
        if (!Objects.equals(DatabaseMaster.getDatabaseMaster().getCityById(arg).getUserId(), DatabaseMaster.getDatabaseMaster().getIdByUser(user))){
            Message<String, ?> message = new Message<>("This element does not belong to you.");
            message.setResult(false);
            return message;
        }
        if (!DatabaseMaster.getDatabaseMaster().update(arg, arg2)) throw new SQLException();
        arg2.setUserId(DatabaseMaster.getDatabaseMaster().getIdByUser(user));
        collection.updateById(arg, arg2);
        Message<String, ?> message = new Message<>("Successfully updated.");
        message.setResult(true);
        return message;
    }
}
