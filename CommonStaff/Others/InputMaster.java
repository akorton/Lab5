package Lab5.CommonStaff.Others;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.RecursionInFileException;

/**
 * class contains all needed methods for all InputMaster classes
 */
public abstract class InputMaster {

    /**
     * main method of the InputMaster
     * @throws RecursionInFileException the recursion may be found in script
     */
    public abstract String run() throws RecursionInFileException;

    /**
     * method that returns Object that was inputted
     * @return Object that was inputted
     */
    public City inputCity(){
        return inputCity(new City());
    }

    /**
     * same as the one before it, but for the given Object
     * @param city object fields of which would be modified
     * @return inputted object
     */
    public abstract City inputCity(City city);

    protected boolean validateNumberOfArgs(int number, String[] line) throws WrongNumberOfArguments {
        if (number != line.length - 1) {
            throw new WrongNumberOfArguments();
        }
        return true;
    }

    /**
     * method that stops the run method
     */
    public abstract void exit();

}
