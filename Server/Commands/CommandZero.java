package Lab5.Server.Commands;

import Lab5.CommonStaff.CommandTypes;
import Lab5.Server.City;
import Lab5.Server.MyCollection;

public abstract class CommandZero<T extends City> extends Command<T>{

    public CommandZero(MyCollection<T> collection){
        super(collection);
    }

    public abstract String execute();
}
