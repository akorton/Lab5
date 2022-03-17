package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public abstract  class Command<T extends City> {
    public abstract void setCollection(MyCollection<T> collection);
    public abstract void execute();
}
