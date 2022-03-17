package Lab5.Client;

import Lab5.Server.City;

import java.util.ArrayList;

/**
 * class contains all needed methods for all InputMaster classes
 * @param <T>
 */
public abstract class InputMaster<T extends City> {

    /**
     * main method of the InputMaster
     * @throws RecursionInFileException the recursion may be found in script
     */
    public abstract void run() throws RecursionInFileException;

    /**
     * method that returns Object that was inputted
     * @return Object that was inputted
     */
    public T inputCity(){
        return inputCity((T) new City());
    }

    /**
     * same as the one before it, but for the given Object
     * @param city object fields of which would be modified
     * @return inputted object
     */
    public abstract T inputCity(T city);

    /**
     * method that stops the run method
     */
    public abstract void exit();

    /**
     * @return arraylist of scripts that are in the process of executing now
     */
    public abstract ArrayList<String> getFilesStack();
}
