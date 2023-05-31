package com.example.fitapp;


import androidx.annotation.NonNull;

import java.util.ArrayList;


public class LocationModel {

    private String firstPossition, lastPossition, tourSQL;
    private int id, type;
    private String initTime, endTime, time;
    private ArrayList<String> tour;
    private double distance;

    public LocationModel() {
    }

    public LocationModel(String firstPossition, String lastPossition, int id, int type, String initTime, String endTime, String time, ArrayList<String> tour, double distance) {
        this.firstPossition = firstPossition;
        this.lastPossition = lastPossition;
        this.id = id;
        this.type = type;
        this.initTime = initTime;
        this.endTime = endTime;
        this.time = time;
        this.tour = tour;
        this.distance = distance;
    }

    public LocationModel(int id, String firstPossition, String lastPossition, String tourSQL,  String initTime, String endTime, String time,  double distance, int type) {
        this.firstPossition = firstPossition;
        this.lastPossition = lastPossition;
        this.id = id;
        this.type = type;
        this.initTime = initTime;
        this.endTime = endTime;
        this.time = time;
        this.tourSQL = tourSQL;
        this.distance = distance;
    }

    public void setFirstPossition(String firstPossition) {
        this.firstPossition = firstPossition;
    }

    public void setLastPossition(String lastPossition) {
        this.lastPossition = lastPossition;
    }

    public String getFirstPossition() {
        return firstPossition;
    }

    public String getLastPossition() {
        return lastPossition;
    }

    @NonNull
    @Override
    public String toString() {
        return "LocationModel{" +
                "firstPossition=" + firstPossition +
                ", lastPossition=" + lastPossition +
                ", id=" + id +
                ", initTime='" + initTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", distance=" + distance +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTour() {
        String tourTogether = "";
        if(tour.size() != 0) {
            for (int i = 0; i < tour.size(); i++) {
                tourTogether += tour.get(i) + ":";
            }
        }
        return tourTogether;
    }

    public void setTour(ArrayList<String> tour) {
        this.tour = tour;
    }

    public String getInitTime() {
        return initTime;
    }

    public void setInitTime(String initTime) {
        this.initTime = initTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}