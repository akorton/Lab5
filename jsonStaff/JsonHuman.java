package Lab5.jsonStaff;

import Lab5.CollectionStaff.Human;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

public class JsonHuman implements JsonSerializer<Human>, JsonDeserializer<Human> {

    public JsonObject serialize(Human human, Type type, JsonSerializationContext context){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("age", new JsonPrimitive(human.getAge()));
        jsonObject.add("birthday", new JsonPrimitive(human.getBirthday().toString()));
        return jsonObject;
    }

    public Human deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int age = jsonObject.get("age").getAsInt();
        ZonedDateTime birthday = ZonedDateTime.parse(jsonObject.get("birthday").getAsString());
        return new Human(age, birthday);
    }
}
