package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class RemoveByIdCommand extends CommandOne<Long>{

    public RemoveByIdCommand(MyCollection collection, Long arg){
        super(collection, arg);
    }

    public String execute() {
        if (!collection.removeCityById(arg)) return "No such id in collection.";
        return "Successfully removed.";
    }
}
