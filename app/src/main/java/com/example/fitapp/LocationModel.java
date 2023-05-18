package com.example.fitapp;

import android.location.Location;

import androidx.annotation.NonNull;

import java.util.Locale;

public class LocationModel {

    public Location possition;
    public double latitude, longitude, distance, totalDistance, step = 0.85;

    public LocationModel(Location possition, Location lastPossition, double latitude, double longitude, double distance, double totalDistance, double step) {
        this.possition = possition;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.totalDistance = totalDistance;
        this.step = step;
    }

    @NonNull
    @Override
    public String toString() {
        return "\nszerokość " +
                latitude + "\ndługość  " +
                longitude + "\nodległość " +
                String.format(Locale.UK, "%10.1f", distance) + "\ncałkowita " +
                String.format("%10.1f", totalDistance) + "\nliczba kroków " +
                String.format("%06d", (int) (totalDistance / step));
    }
}
