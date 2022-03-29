package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

import java.util.NoSuchElementException;

public class ShowCommand<T extends City> extends CommandZero<T>{

    public ShowCommand(MyCollection<T> collection){
        super(collection);
    }

    public String execute() {
        try {
            return collection.getMyCollection().stream()
                    .map(t -> t.toString())
                    .reduce((s, s2) -> s + s2).get();
        } catch (NoSuchElementException e){
            return "No elements in collection.";
        }
    }
}
