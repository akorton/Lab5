package Lab5.Server.Commands;

import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public abstract  class Command {
    protected MyCollection collection;

    public Command(){}

    public Command(MyCollection collection){
        this.collection = collection;
    }
    public abstract Message<String, ?> execute() throws RecursionInFileException;
}
