package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Travel {

    @Id
    private String travelId;
    private String userId;
    private String travelName;



}
