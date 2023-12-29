package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class TravelPlace {

    @Id
    private String placeId;
    private String travelId;
    private String placeName;
    private String travelExperience;
    private String travelDate;

}
