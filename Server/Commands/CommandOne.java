package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public abstract class CommandOne<U> extends Command {
    protected U arg;

    public CommandOne(){

    }

    public CommandOne(MyCollection collection, U arg){
        super(collection);
        this.arg = arg;
    }
    public abstract Message<String, ?> execute() throws RecursionInFileException;

}
