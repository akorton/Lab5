package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public class UpdateCommand<T extends City, U extends Long, V extends T> extends CommandTwo<T, U, V>{

    public UpdateCommand(MyCollection<T> collection, U arg1, V arg2){
        super(collection, arg1, arg2);
    }

    public String execute() {
        if (!collection.updateById(arg, arg2)) return "No such id in collection.";
        return "Successfully updated.";
    }
}
