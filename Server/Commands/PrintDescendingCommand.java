package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

import java.util.Comparator;
import java.util.LinkedList;

public class PrintDescendingCommand extends CommandZero {

    public PrintDescendingCommand(MyCollection collection){
        super(collection);
    }

    public String execute(){
        LinkedList<City> cur = collection.getMyCollection();
        if (cur.size() == 0) return "No elements in collection.";
        return cur.stream().sorted(Comparator.reverseOrder())
                .map(City::toString)
                .reduce((s1, s2)->s1+s2)
                .get();
    }
}
