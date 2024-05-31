package com.example.android.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Personal {
    @Id
    private String userId;
    private String background;
}
