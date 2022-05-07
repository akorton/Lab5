package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

public class ReorderCommand extends CommandZero{

    public ReorderCommand(MyCollection collection){
        super(collection);
    }

    public Message<String, ?> execute(){
        collection.reorder();
        Message<String, ?> message = new Message<>("Successfully reordered.");
        message.setResult(true);
        return message;
    }
}
