package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Likes {
    String postId;
    String userId;
    @Id
    String likeId;
    public Likes() {

    }

    public Likes(String postId, String userId, String likeId) {
        this.postId = postId;
        this.userId = userId;
        this.likeId = likeId;
    }
}
