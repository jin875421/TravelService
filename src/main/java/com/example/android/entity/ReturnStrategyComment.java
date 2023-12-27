package com.example.android.entity;

import java.util.List;

public class ReturnStrategyComment {
    private String commentId;
    private String username;
    private String postId;
    private String comment;
    private String uploadTime;
    private String avatar;
    private String userId;
    private List<ReturnStrategyCommentRespond> returnStrategyCommentResponds;

    public ReturnStrategyComment(String commentId, String username, String postId, String comment, String uploadTime, String avatar, String userId, List<ReturnStrategyCommentRespond> returnCommentResponds) {
        this.commentId = commentId;
        this.username = username;
        this.postId = postId;
        this.comment = comment;
        this.uploadTime = uploadTime;
        this.avatar = avatar;
        this.userId = userId;
        this.returnStrategyCommentResponds = returnCommentResponds;
    }

    @Override
    public String toString() {
        return "ReturnStrategyComment{" +
                "commentId='" + commentId + '\'' +
                ", username='" + username + '\'' +
                ", postId='" + postId + '\'' +
                ", comment='" + comment + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userId='" + userId + '\'' +
                ", returnStrategyCommentResponds=" + returnStrategyCommentResponds +
                '}';
    }

    public List<ReturnStrategyCommentRespond> getReturnCommentResponds() {
        return returnStrategyCommentResponds;
    }

    public void setReturnCommentResponds(List<ReturnStrategyCommentRespond> returnStrategyCommentResponds) {
        this.returnStrategyCommentResponds = returnStrategyCommentResponds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
