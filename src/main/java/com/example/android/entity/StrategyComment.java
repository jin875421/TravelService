package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StrategyComment {
    private String strategyId;
    private String userId;
    private String text;
    @Id
    private String commentId;
    private String time;
    private String parentId;

    @Override
    public String toString() {
        return "Comment{" +
                "postId='" + strategyId + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", commentId='" + commentId + '\'' +
                ", time='" + time + '\'' +
                ", parentId'" + parentId + '\'' +
                '}';
    }

    public StrategyComment(String strategyId, String userId, String text, String commentId, String time, String parentId) {
        this.strategyId = strategyId;
        this.userId = userId;
        this.text = text;
        this.commentId = commentId;
        this.time = time;
        this.parentId = parentId;
    }

    public StrategyComment(){}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPostId() {
        return strategyId;
    }

    public void setPostId(String strategyId) {
        this.strategyId = strategyId;
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
