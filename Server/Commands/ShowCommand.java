package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.MyCollection;

import java.util.NoSuchElementException;

public class ShowCommand extends CommandZero{

    public ShowCommand(MyCollection collection, User user){
        super(collection, user);
    }

    public Message<String, ?> execute() throws UnauthorizedException {
        checkUnauthorized();
        try {
            Message<String, ?> message = new Message<>(collection.getMyCollection().stream()
                    .map(City::toString)
                    .reduce((s, s2) -> s + s2).get());
            message.setResult(true);
            return message;
        } catch (NoSuchElementException e){
            Message<String, ?> message = new Message<>("No elements in collection.");
            message.setResult(false);
            return message;
        }
    }
}
