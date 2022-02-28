package Lab5.jsonStaff;

import Lab5.CollectionStaff.Coordinates;
import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonCoordinates implements JsonSerializer<Coordinates>, JsonDeserializer<Coordinates> {

    public JsonElement serialize(Coordinates coordinates, Type type, JsonSerializationContext context){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("X", new JsonPrimitive(coordinates.getX()));
        jsonObject.add("Y", new JsonPrimitive(coordinates.getY()));
        return jsonObject;
    }

    public Coordinates deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Double X = jsonObject.get("X").getAsDouble();
        Float Y = jsonObject.get("Y").getAsFloat();
        return new Coordinates(X, Y);
    }
}
