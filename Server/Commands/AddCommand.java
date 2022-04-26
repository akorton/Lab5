package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class AddCommand extends CommandOne<City>{

    public AddCommand(MyCollection collection, City arg){
        super(collection, arg);
    }

    public String execute(){
        arg.setId(MyCollection.generateNextId());
        collection.addLast(arg);
        return "Successfully added.";
    }
}
