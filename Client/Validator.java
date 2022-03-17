package Lab5.Client;

import Lab5.Server.Climate;
import Lab5.Server.StandartOfLiving;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/**
 * class that encapsulates all validating functions
 * all the methods are static, so it does not need a constructor
 */
public class Validator {

    private Validator(){

    }

    /**
     * validates name
     * @param name name String
     * @return true if the name was correct false otherwise
     */
    public static boolean validateName(String name){
        return name != null && !(name.lastIndexOf(" ") == name.length() - 1);
    }

    /**
     * validates x
     * @param x x String
     * @return true if the x was correct false otherwise
     */
    public static boolean validateX(String x){
        try{
            double X = Double.parseDouble(x);
            return X > -550;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * validates y
     * @param y y String
     * @return true if the y was correct false otherwise
     */
    public static boolean validateY(String y){
        try{
            float Y = Float.parseFloat(y);
            return Y > -596;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * validates area
     * @param area area String
     * @return true if the area was correct false otherwise
     */
    public static boolean validateArea(String area){
        try{
            double a = Double.parseDouble(area);
            return a > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * validates population
     * @param population population String
     * @return true if the population was correct false otherwise
     */
    public static boolean validatePopulation(String population){
        try{
            long p = Long.parseLong(population);
            return p > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * validates any float value
     * @param s float value String
     * @return true if the float value was correct false otherwise
     */
    public static boolean validateFloatValue(String s){
        try{
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * checks if the given String is one of the Climate enum values
     * @param s name of the enum value String
     * @return true if the given string is Climate enum value false otherwise
     */
    public static boolean validateClimate(String s){
        try{
            Climate.valueOf(s);
            return true;
        } catch (NullPointerException | IllegalArgumentException e){
            return false;
        }
    }

    /**
     * checks if the given String is one of the StandartOfLiving enum values
     * @param s name of the enum value String
     * @return true if the given string is StandartOfLiving enum value false otherwise
     */
    public static boolean validateStandartOfLining(String s){
        try{
            StandartOfLiving.valueOf(s);
            return true;
        } catch (NullPointerException | IllegalArgumentException e){
            return false;
        }
    }

    /**
     * validates age
     * @param age age String
     * @return true if the age was correct false otherwise
     */
    public static boolean validateAge(String age){
        try{
            int age_value = Integer.parseInt(age);
            return age_value > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * validates birthday
     * @param birthday birthday String
     * @return true if the birthday was correct false otherwise
     */
    public static boolean validateBirthday(String birthday){
        try{
            java.time.LocalDate.parse(birthday);
            return true;
        } catch (RuntimeException e){
            return false;
        }
    }

    /**
     * validates any Long value
     * @param s Long value String
     * @return true if the Long value was correct false otherwise
     */
    public static boolean validateLong(String s){
        try{
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * validates ZonedDateTime value
     * @param s ZonedDateTime value String
     * @return true if the value was correct false otherwise
     */
    public static boolean validateZonedDateTime(String s){
        try{
            ZonedDateTime.parse(s);
        } catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}
