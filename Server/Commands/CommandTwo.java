package Lab5.Server.Commands;

import Lab5.Server.MyCollection;

public abstract class CommandTwo<U, V> extends CommandOne<U>{
    protected V arg2;

    public CommandTwo(MyCollection collection, U arg1, V arg2){
        super(collection, arg1);
        this.arg2 = arg2;
    }
}
