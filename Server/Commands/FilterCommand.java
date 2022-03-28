package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public class FilterCommand<T extends City, U extends Float> extends CommandOne<T, U> {

    public FilterCommand(MyCollection<T> collection, U arg){
        super(collection, arg);
    }

    public String execute() {
        return collection.getMyCollection()
                .stream()
                .filter((city)->city.getMetersAboveSeaLevel() > arg.floatValue())
                .toString();
    }
}
