package Lab5.CollectionStaff;

public enum Climate {
    MONSOON("MONSOON"),
    HUMIDSUBTROPICAL("HUMIDSUBTROPICAL"),
    HUMIDCONTINENTAL("HUMIDCONTINENTAL"),
    MEDITERRANIAN("MEDITERRANIAN"),
    STEPPE("STEPPE");

    private final String name;

    Climate(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
