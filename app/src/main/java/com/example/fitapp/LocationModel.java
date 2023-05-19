package com.example.fitapp;

import android.location.Location;

import androidx.annotation.NonNull;

import java.util.Locale;

public class LocationModel {

    public Location firstPossition, lastPossition;
    public int id_l, time, id_tour;

    public LocationModel(Location firstPossition, Location lastPossition, int id_l, int time, int id_tour) {
        this.firstPossition = firstPossition;
        this.lastPossition = lastPossition;
        this.id_l = id_l;
        this.time = time;
        this.id_tour = id_tour;
    }

    public Location getFirstPossition() {
        return firstPossition;
    }

    public void setFirstPossition(Location firstPossition) {
        this.firstPossition = firstPossition;
    }

    public Location getLastPossition() {
        return lastPossition;
    }

    public void setLastPossition(Location lastPossition) {
        this.lastPossition = lastPossition;
    }

    public int getId_l() {
        return id_l;
    }

    public void setId_l(int id_l) {
        this.id_l = id_l;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId_tour() {
        return id_tour;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }
}
