package Lab5.InputStaff;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.MyCollection;

public interface ConsoleExecutable<T extends City> {

    void execute(ConsoleInputMaster<T> consoleInputMaster, MyCollection<T> myCollection, String... args) throws RecursionInFileException;

}
