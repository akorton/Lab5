package Lab5.InputStaff;

import Lab5.CollectionStaff.City;

import java.util.ArrayList;

public abstract class InputMaster<T extends City> {

    public abstract void run() throws Exception;

    public T inputCity(){
        return inputCity((T) new City());
    }
    public abstract T inputCity(T city);
    public abstract void exit();
    public abstract ArrayList<String> getFilesStack();
}
