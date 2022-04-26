package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class InfoCommand extends CommandZero{

    public InfoCommand(MyCollection collection){
        super(collection);
    }

    public String execute() {
        return "Creation time: " + collection.getCreationTime() + "\n" +
                "Collection type: " + collection.getType() + "\n" +
                "Size: " + collection.getSize() + "\n" +
                "First element:\n" + collection.getFirstElement() + "\n" +
                "Last element:\n" + collection.getLastElement();
    }
}
