package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

import java.util.Comparator;
import java.util.LinkedList;

public class PrintDescendingCommand extends CommandZero {

    public PrintDescendingCommand(MyCollection collection){
        super(collection);
    }

    public Message<String, ?> execute(){
        LinkedList<City> cur = collection.getMyCollection();
        if (cur.size() == 0) {
            Message<String, ?> message = new Message<>("No elements in collection.");
            message.setResult(false);
            return message;
        }
        Message<String, ?> message = new Message<>(cur.stream().sorted(Comparator.reverseOrder())
                .map(City::toString)
                .reduce((s1, s2)->s1+s2)
                .get());
        message.setResult(true);
        return message;
    }
}
