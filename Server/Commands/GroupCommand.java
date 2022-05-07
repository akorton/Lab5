package Lab5.Server.Commands;

import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

import java.util.HashMap;
import java.util.Map;

public class GroupCommand extends CommandZero{

    public GroupCommand(MyCollection collection){
        super(collection);
    }

    public Message<String, ?> execute(){
        if (collection.getSize() == 0){
            Message<String, ?> message = new Message<>("No elements in collection.");
            message.setResult(false);
            return message;
        }
        Map<Double, Integer> groups = new HashMap<>();
        collection.getMyCollection().stream()
                .forEach((c)->{
                    if (groups.containsKey(c.getArea())) groups.put(c.getArea(), groups.get(c.getArea())+1);
                    else groups.put(c.getArea(), 1);
                });
        Message<String, ?> message = new Message<>(groups.entrySet().stream()
                .map((entry)-> "Elements with area " + entry.getKey() + ": " + entry.getValue())
                .reduce((s1, s2)->s1+"\n"+s2).get());
        message.setResult(true);
        return message;
    }
}
