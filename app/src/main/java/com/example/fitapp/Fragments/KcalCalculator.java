package com.example.fitapp.Fragments;

import com.example.fitapp.Model.ProfileModel;

public class KcalCalculator {


    public static double calcKcal(int time, double distance, int activity, ProfileModel profile)
    {
     int age = profile.getAge();
     double weight = profile.getWeight();
     boolean isMale = profile.isMale();
     double kcal;
     double MET = 0;
     double hours = time/3600.0;
     double v = calcVelocity(time , distance);
     boolean run = false;
     switch(activity)
     {
         case 0:
             if(v < 20) MET = 7;
             else if(v >= 20 && v < 22) MET = 11;
             else MET = 13;
             break;
         case 1:
             if(v < 20) MET = 7;
             else if(v >= 20 && v < 22) MET = 11;
             else MET = 12;
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

        kcal = (isMale) ? maleActivity(MET, age, weight, hours, run) : femaleActivity(MET, age, weight, hours, run);

     return kcal;
    }


    public static double maleActivity(double MET, int age, double weight, double hours, boolean run)
    {

        if(!run) return ((MET - (0.1 * age)) * weight * hours);
        else return ((MET - (0.2 * age)) * weight * hours);

    }

    public static double femaleActivity(double MET, int age, double weight, double time, boolean run)
    {

        if(!run)return ((MET - ((0.1 * age) - 0.5)) * weight * time);
        else return ((MET - ((0.2 * age) - 0.5)) * weight * time);
    }




    public static double calcVelocity(int time, double distance) {

        double hours = time / 3600;
        distance = distance / 1000;
        return (distance / hours);
    }


}
