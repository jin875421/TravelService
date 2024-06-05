package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    private int id;
    private String imageUrl;
    private String name;
    private String description;

    public Achievement(int id, String imageUrl, String name, String description) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
    }

    public Achievement() {

    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
