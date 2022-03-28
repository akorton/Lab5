package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

import java.util.NoSuchElementException;

public class FilterCommand<T extends City, U extends Float> extends CommandOne<T, U> {

    public FilterCommand(MyCollection<T> collection, U arg){
        super(collection, arg);
    }

    public String execute() {
        try {
            return collection.getMyCollection()
                    .stream()
                    .filter((city) -> city.getMetersAboveSeaLevel() > arg.floatValue())
                    .map(City::toString)
                    .reduce((c1, c2) -> c1 + c2)
                    .get();
        } catch (NoSuchElementException e){
            return "No elements in collection.";
        }
    }
}
