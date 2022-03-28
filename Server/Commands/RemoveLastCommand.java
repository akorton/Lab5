package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class RemoveLastCommand<T extends City> extends CommandZero<T>{

    public RemoveLastCommand(MyCollection<T> collection){
        super(collection);
    }

    public String execute(){
        return collection.removeLast() ? "Last element was removed." : "There is no last element - collection is clear.";
    }
}
