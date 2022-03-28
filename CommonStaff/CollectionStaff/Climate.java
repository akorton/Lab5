package Lab5.CommonStaff.CollectionStaff;

import java.io.Serializable;

public enum Climate implements Serializable {
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
