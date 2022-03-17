package Lab5.Server.Commands;

import Lab5.CommonStaff.CommandTypes;
import Lab5.Server.City;
import Lab5.Server.MyCollection;

public abstract class CommandZero<T extends City> extends Command<T>{
    private final CommandTypes type;
    private MyCollection<T> collection;

    public CommandZero(CommandTypes type){
        this.type = type;
    }
    public void setCollection(MyCollection<T> collection){
        this.collection = collection;
    }

    protected abstract void execute(MyCollection<T> collection);

    public void execute(){
        execute(collection);
    }
}
