package Lab5.CommonStaff.JsonStaff;

import Lab5.CommonStaff.CollectionStaff.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

/**
 * serialize and deserialize City class
 */
public class JsonCity implements JsonSerializer<City>, JsonDeserializer<City> {

    public JsonObject serialize(City city, Type type, JsonSerializationContext context){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("id", new JsonPrimitive(city.getId()));
        jsonObject.add("name", new JsonPrimitive(city.getName()));
        jsonObject.add("coordinates", context.serialize(city.getCoordinates(), Coordinates.class));
        jsonObject.add("creationDate", new JsonPrimitive(city.getCreationDate().toString()));
        jsonObject.add("area", new JsonPrimitive(city.getArea()));
        jsonObject.add("population", new JsonPrimitive(city.getPopulation()));
        jsonObject.add("metersAboveSeaLevel", new JsonPrimitive(city.getMetersAboveSeaLevel()));
        jsonObject.add("agglomeration", new JsonPrimitive(city.getAgglomeration()));
        jsonObject.add("climate", new JsonPrimitive(city.getClimate().toString()));
        jsonObject.add("standartOfLiving", new JsonPrimitive(city.getStandartOfLiving().toString()));
        jsonObject.add("governor", context.serialize(city.getGovernor(), Human.class));
        return jsonObject;
    }

    public City deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Long id = jsonObject.get("id").getAsLong();
        String name = jsonObject.get("name").getAsString();
        Coordinates coordinates = context.deserialize(jsonObject.get("coordinates").getAsJsonObject(), Coordinates.class);
        ZonedDateTime creationDate = context.deserialize(new JsonPrimitive(jsonObject.get("creationDate").getAsString()), ZonedDateTime.class);
        double area = jsonObject.get("area").getAsDouble();
        Long population = jsonObject.get("population").getAsLong();
        float metersAboveSeaLevel = jsonObject.get("metersAboveSeaLevel").getAsFloat();
        float agglomeration = jsonObject.get("agglomeration").getAsFloat();
        Climate climate = context.deserialize(jsonObject.get("climate"), Climate.class);
        StandartOfLiving standartOfLiving = context.deserialize(jsonObject.get("standartOfLiving"), StandartOfLiving.class);
        Human governor = context.deserialize(jsonObject.get("governor"), Human.class);
        return new City(id, name, coordinates, creationDate, area, population, metersAboveSeaLevel, agglomeration, climate, standartOfLiving, governor);
    }
}
