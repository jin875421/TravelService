package com.example.android.entity;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Personal {
    @Id
    private String userId;
    private String background;
}
