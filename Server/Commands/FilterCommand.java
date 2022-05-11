package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.MyCollection;

import java.util.NoSuchElementException;

public class FilterCommand extends CommandOne<Float> {

    public FilterCommand(MyCollection collection, User user, Float arg){
        super(collection, user, arg);
    }

    public Message<String, ?> execute() throws UnauthorizedException {
        checkUnauthorized();
        try {
            Message<String, ?> message = new Message<>(collection.getMyCollection()
                    .stream()
                    .filter((city) -> city.getMetersAboveSeaLevel() > arg)
                    .map(City::toString)
                    .reduce((c1, c2) -> c1 + c2)
                    .get());
            message.setResult(true);
            return message;
        } catch (NoSuchElementException e){
            Message<String, ?> message = new Message<>("No elements in collection.");
            message.setResult(false);
            return message;
        }
    }
}
