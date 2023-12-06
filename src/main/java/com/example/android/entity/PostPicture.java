package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class PostPicture {
    @Id
    String pictureId;
    String picturePath;
    String postId;

    public PostPicture(String pictureId, String picturePath, String postId) {
        this.pictureId = pictureId;
        this.picturePath = picturePath;
        this.postId = postId;
    }

    public PostPicture() {

    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
