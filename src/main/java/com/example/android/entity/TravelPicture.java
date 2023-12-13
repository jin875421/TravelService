package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class TravelPicture {

    @Id
    private String pictureId;
    private String picturePath;
    private String placeId;

}
