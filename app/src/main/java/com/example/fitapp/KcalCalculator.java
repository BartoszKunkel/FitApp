package com.example.fitapp;

public class KcalCalculator {

    private double velocity;
    public double calcKcal(int age, double weight, int time, double distance, int activity, boolean isMale)
    {
     double kcal = 0;
     double MET = 0;
     double v = calcVelocity(time , distance);
     boolean run = false;
     switch(activity)
     {
         case 0:
             if(v < 20) MET = 7;
             else if(v >= 20 && v < 22) MET = 11;
             else MET = 11;
             break;
         case 1:
             if(v < 20) MET = 7;
             else if(v >= 20 && v < 22) MET = 11;
             else MET = 11;
             break;
         case 2:
             MET = 3.5;
             break;
         case 3:
             run = true;
             if(v < 6.67) MET = 6;
             else if(v >= 6.67 && v < 8.5) MET = 8;
             else if(v >= 8.5 && v < 12) MET = 9;
             else MET = 10;
             break;
     }
        kcal = (isMale) ? maleActivity(MET, age, weight, time, run) : femaleActivity(MET, age, weight, time, run);
        return kcal;
    }


    public double maleActivity(double MET, int age, double weight, int time, boolean run)
    {
        time = time/3600;
        if(!run) return MET - (0.1 * age) * weight * time;
        else return MET - (0.2 * age) * weight * time;

    }

    public double femaleActivity(double MET, int age, double weight, int time, boolean run)
    {
        time = time/3600;
        if(!run)return MET - ((0.1 * age) - 0.5) * weight * time;
        else return MET - ((0.2 * age) - 0.5) * weight * time;
    }




    public double calcVelocity(int time, double distance) {

        double hours = time / 3600;
        distance = distance / 1000;
        velocity = distance / hours;
        return velocity;
    }


}
