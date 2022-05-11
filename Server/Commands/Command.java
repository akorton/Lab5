package Lab5.Server.Commands;

import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

import java.sql.SQLException;
import java.util.Objects;

public abstract  class Command {
    protected MyCollection collection;
    protected User user;

    public Command(){}

    public Command(MyCollection collection, User user){
        this.collection = collection;
        this.user = user;
    }
    public abstract Message<String, ?> execute() throws RecursionInFileException, SQLException, AlreadyAuthorizedException, UnauthorizedException;

    protected void checkAuthorized() throws AlreadyAuthorizedException{
        if (!Objects.equals(user, null)) throw new AlreadyAuthorizedException();
    }

    protected void checkUnauthorized() throws UnauthorizedException{
        if (Objects.equals(user, null)) throw new UnauthorizedException();
    }
}
