package Lab5.CommonStaff.CollectionStaff;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Double x;
    private Float y;

    public Coordinates(){
    }

    public Coordinates(Double X, Float Y){
        this.x = X;
        this.y = Y;
    }

    public Double getX(){
        return x;
    }

    public Float getY(){
        return y;
    }

    public void setX(Double x){
        this.x = x;
    }

    public void setY(Float y){
        this.y = y;
    }

    public String toString(){
        return x + ", " + y;
    }
}
