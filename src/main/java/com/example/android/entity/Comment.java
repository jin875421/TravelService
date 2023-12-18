package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="comment")
//用于显示评论区
public class Comment {
    private String postId;
    private String userId;
    private String text;
    @Id
    private String commentId;
    private String time;
    private String parentId;

    @Override
    public String toString() {
        return "Comment{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", commentId='" + commentId + '\'' +
                ", time='" + time + '\'' +
                ", parentId'" + parentId + '\'' +
                '}';
    }

    public Comment(String postId, String userId, String text, String commentId, String time, String parentId) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.commentId = commentId;
        this.time = time;
        this.parentId = parentId;
    }

    public Comment(){}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parent_id) {
        this.parentId = parent_id;
    }
}

