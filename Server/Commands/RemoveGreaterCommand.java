package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public class RemoveGreaterCommand<T extends City, U extends T>  extends CommandOne<T, U> {

    public RemoveGreaterCommand(MyCollection<T> collection, U arg){
        super(collection, arg);
    }

    public String execute() {
        collection.removeAll(collection.getMyCollection()
                .stream()
                .filter((city)->city.compareTo(arg) > 0)
                .toList());
        return "Successfully removed.";
    }
}
