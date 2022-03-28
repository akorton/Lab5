package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public class ClearCommand<T extends City> extends CommandZero<T>{

    public ClearCommand(MyCollection<T> collection){
        super(collection);
    }

    public String execute(){
        collection.clearCollection();
        return "Collection was successfully cleared.";
    }
}
