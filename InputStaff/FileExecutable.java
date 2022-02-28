package Lab5.InputStaff;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.MyCollection;

public interface FileExecutable<T extends City> {
    void execute(FileInputMaster<T> fileInputMaster, MyCollection<T> myCollection, String... args) throws RecursionInFileException;
}
