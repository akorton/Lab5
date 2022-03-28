package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public class ReorderCommand<T extends City> extends CommandZero<T>{

    public ReorderCommand(MyCollection<T> collection){
        super(collection);
    }

    public String execute(){
        collection.reorder();
        return "Successfully reordered.";
    }
}
