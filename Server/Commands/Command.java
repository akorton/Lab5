package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public abstract  class Command<T extends City> {
    protected MyCollection<T> collection;

    public Command(MyCollection<T> collection){
        this.collection = collection;
    }
    public abstract String execute() throws RecursionInFileException;
}
