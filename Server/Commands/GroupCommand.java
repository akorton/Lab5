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
        for (City city: collection.getMyCollection()){
            double cur_area = city.getArea();
            if (groups.containsKey(cur_area)){
                groups.put(cur_area, groups.get(cur_area)+1);
            } else{
                groups.put(cur_area, 1);
            }
        }
        String ans = "";
        for (Map.Entry<Double, Integer> entry: groups.entrySet()){
            Double key = entry.getKey();
            Integer value = entry.getValue();
            ans += "Elements with area " + key + ": " + value + "\n";
        }
        return ans;
    }
}
