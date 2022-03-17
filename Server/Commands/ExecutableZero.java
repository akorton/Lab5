package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public interface ExecutableZero<T extends City> {
    public void execute(MyCollection<T> collection);
}
