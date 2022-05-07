package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.MyCollection;

public class AddCommand extends CommandOne<City>{

    public AddCommand(MyCollection collection, City arg){
        super(collection, arg);
    }

    public Message<String, ?> execute(){
        arg.setId(MyCollection.generateNextId());
        collection.addLast(arg);
        Message<String, ?> message = new Message<>("Successfully added.");
        message.setResult(true);
        return message;
    }
}
