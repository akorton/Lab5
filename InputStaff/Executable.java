package Lab5.InputStaff;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.MyCollection;

/**
 * interface that is implemented by commands
 * contains only one method which makes it functional interface
 * @param <T>
 */
public interface Executable<T extends City> {

    void execute(InputMaster<T> inputMaster, MyCollection<T> myCollection, String... args) throws RecursionInFileException;

}
