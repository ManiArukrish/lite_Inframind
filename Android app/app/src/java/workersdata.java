package com.example.afinal;

public class workersdata {

    int Heart_Rate,Oxygen_Saturation,temperature;
    public workersdata(){}

    public workersdata(int Heart_Rate,int Oxygen_Saturation,int temperature){
        this.Heart_Rate=Heart_Rate;
        this.Oxygen_Saturation=Oxygen_Saturation;
        this.temperature=temperature;

    }

    public int getHeart_Rate() {
        return Heart_Rate;
    }

    public void setHeart_Rate(int heart_Rate) {
        Heart_Rate = heart_Rate;
    }

    public int getOxygen_Saturation() {
        return Oxygen_Saturation;
    }

    public void setOxygen_Saturation(int oxygen_Saturation) {
        Oxygen_Saturation = oxygen_Saturation;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
