package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

import java.util.stream.Collectors;

public class RemoveGreaterCommand  extends CommandOne<City> {

    public RemoveGreaterCommand(MyCollection collection, City arg){
        super(collection, arg);
    }

    public String execute() {
        collection.removeAll(collection.getMyCollection()
                .stream()
                .filter((city)->city.compareTo(arg) > 0)
                .collect(Collectors.toList()));
        return "Successfully removed.";
    }
}
