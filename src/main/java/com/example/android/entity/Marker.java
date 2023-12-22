package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Marker {
    @Id
    private String markerId;
    private String city;
    private double latitude;
    private double longitude;

    public Marker(String markerId, String city, double latitude, double longitude) {
        this.markerId = markerId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    public Marker() {
    }

    @Override
    public String toString() {
        return "Marker{" +
                "markerId='" + markerId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
