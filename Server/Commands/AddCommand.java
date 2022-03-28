package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public class AddCommand<T extends City, U extends T> extends CommandOne<T, U>{

    public AddCommand(MyCollection<T> collection, U arg){
        super(collection, arg);
    }

    public String execute(){
        collection.addLast(arg);
        return "Successfully added.";
    }
}
