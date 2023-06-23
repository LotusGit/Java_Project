package com.example.controleyassinemchimech;

public class ManagerJunior extends Manager{
    public ManagerJunior(String name, String id, float hours) {
        super(name, id, hours);
    }
    float Cout(){
        if(this.Hours>3000) return  4000+3000+(this.Hours-3000)+3500;
        else if (this.Hours<3000) return this.Hours+3000;
        else{ return 3500+3000;}
    }
}
