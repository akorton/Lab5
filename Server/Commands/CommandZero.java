package Lab5.Server.Commands;

import Lab5.Server.MyCollection;

public abstract class CommandZero extends Command{

    public CommandZero(MyCollection collection){
        super(collection);
    }

    public abstract String execute();
}
