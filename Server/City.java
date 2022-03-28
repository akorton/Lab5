package Lab5.Server;


import Lab5.Client.Validator;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class City extends MyCollection<City> implements Comparable<City>, Serializable {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private final java.time.ZonedDateTime creationDate;
    private double area;
    private Long population;
    private float metersAboveSeaLevel;
    private float agglomeration;
    private Climate climate;
    private StandartOfLiving standartOfLiving;
    private Human governor;

    public City(){
        this(MyCollection.generateNextId());
    }

    public City(Long id){
        this.id = id;
        this.creationDate = java.time.ZonedDateTime.now();
    }

    public Long getId(){
        return id;
    }

    public Human getGovernor(){return governor;}

    public float getMetersAboveSeaLevel(){
        return metersAboveSeaLevel;
    }

    public double getArea() {return area;}

    public void setName(String name){
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public void setArea(double area){
        this.area = area;
    }

    public void setPopulation(Long population){
        this.population = population;
    }

    public void setMetersAboveSeaLevel(float metersAboveSeaLevel){
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setAgglomeration(float agglomeration){
        this.agglomeration = agglomeration;
    }

    public void setClimate(Climate climate){
        this.climate = climate;
    }

    public void setStandartOfLiving(StandartOfLiving standartOfLiving){
        this.standartOfLiving = standartOfLiving;
    }

    public void setGovernor(Human governor){
        this.governor = governor;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public City(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, double area, Long population, float metersAboveSeaLevel, float agglomeration, Climate climate, StandartOfLiving standartOfLiving, Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.climate = climate;
        this.standartOfLiving = standartOfLiving;
        this.governor = governor;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Long getPopulation() {
        return population;
    }

    public float getAgglomeration() {
        return agglomeration;
    }

    public Climate getClimate() {
        return climate;
    }

    public StandartOfLiving getStandartOfLiving() {
        return standartOfLiving;
    }

    public int compareTo(City c1){
        return governor.getAge() - c1.getGovernor().getAge();
    }

    public String toString(){
        String s = "{\n";
        s += "  id: " + id + "\n";
        s += "  name: " + name + "\n";
        s += "  coordinates: [x: " + coordinates.getX() + ", y: " + coordinates.getY() + "]\n";
        s += "  creationDate: " + creationDate.toString().split("T")[0] + "\n";
        s += "  area: " + area + "\n";
        s += "  population: " + population + "\n";
        s += "  metersAboveSeaLevel: " + metersAboveSeaLevel + "\n";
        s += "  agglomeration: " + agglomeration + "\n";
        s += "  climate: " +climate + "\n";
        s += "  standartOfLiving: " + standartOfLiving + "\n";
        s += "  governor: [age: " + governor.getAge() + ", birthday: " + governor.getBirthday().toString().split("T")[0] + "]\n";
        s += "}";
        return s;
    }

    /**
     * Validates all the fields of the city at once
     * @param city city which fields to validate
     * @return true if all the fields are correct false otherwise
     */
    public static boolean validateCity(City city){
        return Validator.validateName(city.getName()) && Validator.validateX(String.valueOf(city.getCoordinates().getX())) &&
                Validator.validateY(String.valueOf(city.getCoordinates().getY())) && Validator.validateAge(String.valueOf(city.getGovernor().getAge())) &&
                Validator.validateArea(String.valueOf(city.getArea())) && Validator.validateClimate(city.getClimate().toString()) &&
                Validator.validatePopulation(city.getPopulation().toString()) &&
                Validator.validateStandartOfLining(city.getStandartOfLiving().toString()) && Validator.validateFloatValue(String.valueOf(city.getMetersAboveSeaLevel())) &&
                Validator.validateFloatValue(String.valueOf(city.getAgglomeration())) && Validator.validateZonedDateTime(city.getCreationDate().toString());
    }
}
