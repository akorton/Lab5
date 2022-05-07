package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

import java.util.stream.Collectors;

public class RemoveGreaterCommand  extends CommandOne<City> {

    public RemoveGreaterCommand(MyCollection collection, City arg){
        super(collection, arg);
    }

    public Message<String, ?> execute() {
        collection.removeAll(collection.getMyCollection()
                .stream()
                .filter((city)->city.compareTo(arg) > 0)
                .collect(Collectors.toList()));
        Message<String, ?> message = new Message<>("Successfully removed.");
        message.setResult(true);
        return message;
    }
}
