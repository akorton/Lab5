package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

public class ClearCommand extends CommandZero{

    public ClearCommand(MyCollection collection){
        super(collection);
    }

    public Message<String, ?> execute(){
        collection.clearCollection();
        Message<String, ?> message = new Message<>("Collection was successfully cleared.");
        message.setResult(true);
        return message;
    }
}
