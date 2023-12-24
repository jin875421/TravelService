package com.example.android.entity;

public class LikeAndStarStatusResponse {
    int likeStatus;
    int starStatus;
    public LikeAndStarStatusResponse(int likeStatus, int starStatus) {
        this.likeStatus = likeStatus;
        this.starStatus = starStatus;
    }
    public LikeAndStarStatusResponse() {
    }
    public int getLikeStatus() {
        return likeStatus;
    }
    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }
    public int getStarStatus() {
        return starStatus;
    }
    public void setStarStatus(int starStatus) {
        this.starStatus = starStatus;
    }

}
