package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ShowCommand<T extends City> extends CommandZero<T>{

    public ShowCommand(MyCollection<T> collection){
        super(collection);
    }

    public String execute(){
         return collection.getMyCollection().stream()
                 .sorted((city1, city2)-> (int) (city1.getId()-city2.getId()))
                 .map(t -> t.toString())
                 .reduce((s, s2) -> s+s2).get();
    }
}
