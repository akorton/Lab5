package Lab5.InputStaff;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.MyCollection;

public interface Executable<T extends City> {

    void execute(InputMaster<T> inputMaster, MyCollection<T> myCollection, String... args) throws RecursionInFileException;

}
