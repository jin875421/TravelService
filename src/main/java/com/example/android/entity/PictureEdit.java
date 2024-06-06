package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class PictureEdit {
    @Id
    String id;
    String userId;
    String pictureUrl;
}
