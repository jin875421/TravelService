package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Star {
    String postId;
    String userId;
    @Id
    String starId;

    public Star() {
    }

    public Star(String postId, String userId, String starId) {
        this.postId = postId;
        this.userId = userId;
        this.starId = starId;
    }
}
