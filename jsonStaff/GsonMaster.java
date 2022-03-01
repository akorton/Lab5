package Lab5.jsonStaff;

import Lab5.CollectionStaff.City;
import Lab5.CollectionStaff.Coordinates;
import Lab5.CollectionStaff.Human;
import Lab5.InputStaff.FileInputMaster;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.LinkedList;

/**
 * class to serialize and deserialize data to and from json
 */
public class GsonMaster {

    public GsonMaster(){

    }

    /**
     * deserialize data from json
     * @param sysVariableName name of the file
     * @return collection build from json data
     */
    public LinkedList<City> deserialize(String sysVariableName){
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder
                    .setPrettyPrinting()
                    .registerTypeAdapter(ZonedDateTime.class, new JsonZonedDateTime())
                    .registerTypeAdapter(Coordinates.class, new JsonCoordinates())
                    .registerTypeAdapter(Human.class, new JsonHuman())
                    .registerTypeAdapter(City.class, new JsonCity());
            FileInputMaster<City> fileInputMaster = new FileInputMaster<City>(System.getenv(sysVariableName));
            String serializedData = fileInputMaster.inputFile();
            if (serializedData == null) return null;
            File file = new File(System.getenv(sysVariableName));
            if (!file.canWrite()){
                System.out.println("WARNING!!!\nFile is not writeable.");
            }
            Gson gson = gsonBuilder.create();
            return gson.fromJson(serializedData, new TypeToken<LinkedList<City>>() {
            }.getType());
        } catch (JsonSyntaxException e){
            System.out.println("Unable to parse from json.");
            return null;
        }
    }

    /**
     * makes json from Object
     * @param o object to convert to json
     * @return json text
     */
    public String serialize(Object o){
        try {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Coordinates.class, new JsonCoordinates())
                    .registerTypeAdapter(ZonedDateTime.class, new JsonZonedDateTime())
                    .registerTypeAdapter(Human.class, new JsonHuman())
                    .registerTypeAdapter(City.class, new JsonCity());
            Gson gson = gsonBuilder.create();
            return gson.toJson(o);
        } catch (JsonIOException e){
            System.out.println("Unable to parse to json.");
            return null;
        }
    }
}
