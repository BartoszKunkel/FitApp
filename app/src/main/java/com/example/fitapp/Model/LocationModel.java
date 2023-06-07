package com.example.fitapp.Model;


import androidx.annotation.NonNull;

import java.util.ArrayList;


public class LocationModel {

    private String firstPosition, lastPosition, tourSQL;
    private int id, type;
    private String initTime, endTime, time;
    private ArrayList<String> tour;
    private double distance;

    public LocationModel() {
    }

    public LocationModel(String firstPosition, String lastPosition, int id, int type, String initTime, String endTime, String time, ArrayList<String> tour, double distance) {
        this.firstPosition = firstPosition;
        this.lastPosition = lastPosition;
        this.id = id;
        this.type = type;
        this.initTime = initTime;
        this.endTime = endTime;
        this.time = time;
        this.tour = tour;
        this.distance = distance;
    }

    public LocationModel(int id, String firstPosition, String lastPosition, String tourSQL,  String initTime, String endTime, String time,  double distance, int type) {
        this.firstPosition = firstPosition;
        this.lastPosition = lastPosition;
        this.id = id;
        this.type = type;
        this.initTime = initTime;
        this.endTime = endTime;
        this.time = time;
        this.tourSQL = tourSQL;
        this.distance = distance;
    }

    public void setFirstPosition(String firstPosition) {
        this.firstPosition = firstPosition;
    }

    public void setLastPosition(String lastPosition) {
        this.lastPosition = lastPosition;
    }

    public String getFirstPosition() {
        return firstPosition;
    }

    public String getLastPosition() {
        return lastPosition;
    }

    @NonNull
    @Override
    public String toString() {
        return "LocationModel{" +
                "firstPosition=" + firstPosition +
                ", lastPosition=" + lastPosition +
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