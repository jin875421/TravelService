package com.example.androdid.entity;

import java.util.List;

public class PostItem {
    String postId;
    String postTitle;
    String postContent;
    String userId;
    String createTime;
    List<String> picturePath;
    int PictureNumber;

    public PostItem(String postId, String postTitle, String postContent, String userId, String createTime, List<String> picturePath, int pictureNumber) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.userId = userId;
        this.createTime = createTime;
        this.picturePath = picturePath;
        PictureNumber = pictureNumber;
    }
    public PostItem(){

    }
    public int getPictureNumber() {
        return PictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        PictureNumber = pictureNumber;
    }

    public List<String> getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(List<String> path) {
        this.picturePath = path;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
