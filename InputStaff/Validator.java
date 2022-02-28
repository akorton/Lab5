package Lab5.InputStaff;

import Lab5.CollectionStaff.Climate;
import Lab5.CollectionStaff.StandartOfLiving;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class Validator {

    public Validator(){

    }

    public static boolean validateName(String name){
        return name != null && !name.isBlank();
    }

    public static boolean validateX(String x){
        try{
            double X = Double.parseDouble(x);
            return X > -550;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validateY(String y){
        try{
            float Y = Float.parseFloat(y);
            return Y > -596;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validateArea(String area){
        try{
            double a = Double.parseDouble(area);
            return a > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validatePopulation(String population){
        try{
            long p = Long.parseLong(population);
            return p > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validateFloatValue(String s){
        try{
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validateClimate(String s){
        try{
            Climate.valueOf(s);
            return true;
        } catch (NullPointerException | IllegalArgumentException e){
            return false;
        }
    }

    public static boolean validateStandartOfLining(String s){
        try{
            StandartOfLiving.valueOf(s);
            return true;
        } catch (NullPointerException | IllegalArgumentException e){
            return false;
        }
    }

    public static boolean validateAge(String s){
        try{
            int age = Integer.parseInt(s);
            return age > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validateBirthday(String s){
        try{
            java.time.LocalDate.parse(s);
            return true;
        } catch (RuntimeException e){
            return false;
        }
    }

    public static boolean validateLong(String s){
        try{
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validateZonedDateTime(String s){
        try{
            ZonedDateTime.parse(s);
        } catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}
