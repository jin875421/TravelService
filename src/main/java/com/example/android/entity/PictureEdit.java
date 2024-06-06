package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class PictureEdit {
    @Id
    String userId;
    String pictureUrl;
}
