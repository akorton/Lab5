package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public interface ExecutableOne<T extends City, U> {
    public void execute(MyCollection<T> collection, U arg);
}
