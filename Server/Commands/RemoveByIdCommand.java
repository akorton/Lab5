package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

public class RemoveByIdCommand extends CommandOne<Long>{

    public RemoveByIdCommand(MyCollection collection, Long arg){
        super(collection, arg);
    }

    public Message<String, ?> execute() {
        if (!collection.removeCityById(arg)) {
            Message<String, ?> message = new Message<>("No such id in collection.");
            message.setResult(false);
            return message;
        }
        Message<String, ?> message = new Message<>("Successfully removed.");
        message.setResult(true);
        return message;
    }
}
