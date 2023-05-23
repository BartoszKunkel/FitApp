package com.example.fitapp;

public class ProfileModel {

    int id_profile;
    double weight;
    boolean isMale;
    int age;


    public ProfileModel(int id_profile, double weight, boolean isMale, int age) {
        this.id_profile = id_profile;
        this.weight = weight;
        this.isMale = isMale;
        this.age = age;
    }

    public int getId_profile() {
        return id_profile;
    }

    public void setId_profile(int id_profile) {
        this.id_profile = id_profile;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
