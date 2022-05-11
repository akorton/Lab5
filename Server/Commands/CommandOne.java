package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

import java.sql.SQLException;

public abstract class CommandOne<U> extends Command {
    protected U arg;

    public CommandOne(MyCollection collection, User user, U arg){
        super(collection, user);
        this.arg = arg;
    }
}
