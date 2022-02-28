package Lab5.jsonStaff;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

public class JsonZonedDateTime implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

    public JsonElement serialize(ZonedDateTime time, Type type, JsonSerializationContext context){
        return new JsonPrimitive(time.toString());
    }

    public ZonedDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context){
        return ZonedDateTime.parse(jsonElement.getAsString());
    }
}
