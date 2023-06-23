package com.example.controleyassinemchimech;

import java.io.Serializable;

public abstract class Manager implements Serializable {
    String name;
    String Id;
    float Hours;

    public Manager(String name, String id, float hours) {
        this.name = name;
        Id = id;
        Hours = hours;
    }
    abstract float Cout();

    public String toString() {
        return Id + ", " + name + ", " + this.Hours + " heures , " +this.getClass().getName()+", "+ this.Cout()+ " dhs" + "\n";
    }
}
