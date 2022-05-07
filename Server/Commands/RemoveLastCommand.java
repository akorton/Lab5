package Lab5.Server.Commands;

import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

public class RemoveLastCommand extends CommandZero{

    public RemoveLastCommand(MyCollection collection){
        super(collection);
    }

    public Message<String, ?> execute(){
        Message<String, ?> message = new Message<>( collection.removeLast() ? "Last element was removed." : "There is no last element - collection is clear.");
        message.setResult(true);
        return message;
    }
}
