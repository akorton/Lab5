package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public abstract class CommandOne<T extends City, U> extends Command<T> {
    protected final U arg;

    public CommandOne(MyCollection<T> collection, U arg){
        super(collection);
        this.arg = arg;
    }
    public abstract String execute() throws RecursionInFileException;

}
