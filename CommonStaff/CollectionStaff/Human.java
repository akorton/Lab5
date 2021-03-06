package Lab5.CommonStaff.CollectionStaff;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Human implements Serializable {
    private int age;
    private java.time.ZonedDateTime birthday;

    public Human(){

    }

    public Human(int age, ZonedDateTime birthday){
        this.age = age;
        this.birthday = birthday;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setBirthday(java.time.ZonedDateTime birthday){
        this.birthday = birthday;
    }

    public java.time.ZonedDateTime getBirthday(){
        return birthday;
    }

    public String toString(){
        return age + ", " + birthday;
    }
}
