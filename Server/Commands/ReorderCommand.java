package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class ReorderCommand extends CommandZero{

    public ReorderCommand(MyCollection collection){
        super(collection);
    }

    public String execute(){
        collection.reorder();
        return "Successfully reordered.";
    }
}
