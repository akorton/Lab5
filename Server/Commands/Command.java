package Lab5.Server.Commands;

import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public abstract  class Command {
    protected MyCollection collection;

    public Command(MyCollection collection){
        this.collection = collection;
    }
    public abstract String execute() throws RecursionInFileException;
}
