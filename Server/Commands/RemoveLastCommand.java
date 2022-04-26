package Lab5.Server.Commands;

import Lab5.Server.MyCollection;

public class RemoveLastCommand extends CommandZero{

    public RemoveLastCommand(MyCollection collection){
        super(collection);
    }

    public String execute(){
        return collection.removeLast() ? "Last element was removed." : "There is no last element - collection is clear.";
    }
}
