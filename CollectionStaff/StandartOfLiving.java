package Lab5.CollectionStaff;

public enum StandartOfLiving {
    ULTRA_HIGH("ULTRA_HIGH"),
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    VERY_LOW("VERY_LOW");

    private final String name;

    StandartOfLiving(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
