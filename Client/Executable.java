package Lab5.Client;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

/**
 * interface that is implemented by commands
 * contains only one method which makes it functional interface
 * @param <T>
 */
public interface Executable<T extends City> {

    void execute(InputMaster<T> inputMaster, MyCollection<T> myCollection, String... args) throws RecursionInFileException;

}
