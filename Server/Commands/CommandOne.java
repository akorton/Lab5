package Lab5.Server.Commands;

import Lab5.CommonStaff.CommandTypes;
import Lab5.Server.City;
import Lab5.Server.MyCollection;

public abstract class CommandOne<T extends City, U> extends Command<T> {
    private final CommandTypes type;
    private MyCollection<T> collection;
    private final U arg;

    public CommandOne(CommandTypes type, U arg){
        this.type = type;
        this.arg = arg;
    }

    public void setCollection(MyCollection<T> collection) {
        this.collection = collection;
    }

    protected abstract void execute(MyCollection<T> collection, U arg);

    public void execute(){
        execute(collection, arg);
    }
}
