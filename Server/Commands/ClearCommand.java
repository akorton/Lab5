package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class ClearCommand extends CommandZero{

    public ClearCommand(MyCollection collection){
        super(collection);
    }

    public String execute(){
        collection.clearCollection();
        return "Collection was successfully cleared.";
    }
}
