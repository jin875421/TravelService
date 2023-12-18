package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Like {
    String postId;
    String userId;
    @Id
    String likeId;
    public Like() {

    }

    public Like(String postId, String userId, String likeId) {
        this.postId = postId;
        this.userId = userId;
        this.likeId = likeId;
    }
}
