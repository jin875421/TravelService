package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_follow")
@Data
public class Follow {
    @Id
    private String id;
    private String userId;
    private String followId;

    public Follow(String id, String userId, String followId) {
        this.id = id;
        this.userId = userId;
        this.followId = followId;
    }

    public Follow() {

    }
}
