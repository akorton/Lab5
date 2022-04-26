package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class UpdateCommand extends CommandTwo<Long, City>{

    public UpdateCommand(MyCollection collection, Long arg1, City arg2){
        super(collection, arg1, arg2);
    }

    public String execute() {
        if (!collection.updateById(arg, arg2)) return "No such id in collection.";
        return "Successfully updated.";
    }
}
