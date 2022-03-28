package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public abstract class CommandTwo<T extends City, U, V> extends CommandOne<T, U>{
    protected V arg2;

    public CommandTwo(MyCollection<T> collection, U arg1, V arg2){
        super(collection, arg1);
        this.arg2 = arg2;
    }
}
