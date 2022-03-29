package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

import java.util.HashMap;
import java.util.Map;

public class GroupCommand<T extends City> extends CommandZero<T>{

    public GroupCommand(MyCollection<T> collection){
        super(collection);
    }

    public String execute(){
        if (collection.getSize() == 0){
            return "No elements in collection.";
        }
        Map<Double, Integer> groups = new HashMap<>();
        collection.getMyCollection().stream()
                .forEach((c)->{
                    if (groups.containsKey(c.getArea())) groups.put(c.getArea(), groups.get(c.getArea())+1);
                    else groups.put(c.getArea(), 1);
                });
        String ans = groups.entrySet().stream()
                .map((entry)-> "Elements with area " + entry.getKey() + ": " + entry.getValue())
                .reduce((s1, s2)->s1+"\n"+s2).get();
        return ans;
    }
}
