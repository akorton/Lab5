package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

public class UpdateCommand extends CommandTwo<Long, City>{

    public UpdateCommand(MyCollection collection, Long arg1, City arg2){
        super(collection, arg1, arg2);
    }

    public Message<String, ?> execute() {
        if (!collection.updateById(arg, arg2)) {
            Message<String, ?> message = new Message<>("No such id in collection.");
            message.setResult(false);
            return message;
        }
        Message<String, ?> message = new Message<>("Successfully updated.");
        message.setResult(true);
        return message;
    }
}
