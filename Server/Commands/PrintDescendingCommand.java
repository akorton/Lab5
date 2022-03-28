package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

import java.util.Comparator;
import java.util.LinkedList;

public class PrintDescendingCommand<T extends City> extends CommandZero<T> {

    public PrintDescendingCommand(MyCollection<T> collection){
        super(collection);
    }

    public String execute(){
        LinkedList<T> cur = collection.getMyCollection();
        if (cur.size() == 0) System.out.println("No elements in collection.");
        return cur.stream().sorted(Comparator.reverseOrder()).toString();
    }
}
