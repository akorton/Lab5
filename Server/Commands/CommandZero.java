package Lab5.Server.Commands;

import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.MyCollection;

import java.sql.SQLException;

public abstract class CommandZero extends Command{

    public CommandZero(MyCollection collection, User user){
        super(collection, user);
    }
}
