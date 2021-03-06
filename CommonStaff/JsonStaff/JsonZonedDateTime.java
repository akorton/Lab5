package Lab5.CommonStaff.JsonStaff;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

/**
 * serialize and deserialize ZonedDateTime
 */
public class JsonZonedDateTime implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

    public JsonElement serialize(ZonedDateTime time, Type type, JsonSerializationContext context){
        return new JsonPrimitive(time.toString());
    }

    public ZonedDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context){
        return ZonedDateTime.parse(jsonElement.getAsString());
    }
}
