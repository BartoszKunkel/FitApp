package com.example.fitapp;

public class TourModel {
    public int id_tour;
    public String tour;

    public TourModel(int id_tour, String tour) {
        this.id_tour = id_tour;
        this.tour = tour;
    }

    public int getId_tour() {
        return id_tour;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }
}
