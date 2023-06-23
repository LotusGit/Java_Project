package com.example.controleyassinemchimech;

public class ManagerSenior extends Manager{
    public ManagerSenior(String name, String id, float hours) {
        super(name, id, hours);
    }
    float Cout(){
        if(this.Hours>2000) return  2500*2000+(this.Hours-2000)*3500;
        else if (this.Hours<2000) return this.Hours+2000;
        else{ return 2500+2000;}
    }
}
