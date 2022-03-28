package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class RemoveByIdCommand<T extends City, U extends Long> extends CommandOne<T, U>{

    public RemoveByIdCommand(MyCollection<T> collection, U arg){
        super(collection, arg);
    }

    public String execute() {
        if (!collection.removeCityById(arg)) return "No such id in collection.";
        return "Successfully removed.";
    }
}
