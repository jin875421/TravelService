package com.example.android.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CommentRespond {
    @Id
    @Value("comment_respond_id")
    private String commentRespondId;
    private String userId;
    private String text;
    private String time;
    private String commentId;

    public CommentRespond(String commentRespondId, String userId, String text, String time, String commentId) {
        this.commentRespondId = commentRespondId;
        this.userId = userId;
        this.text = text;
        this.time = time;
        this.commentId = commentId;
    }

    public CommentRespond() {

    }

    public String toString() {
        return "CommentRespond{" +
                "commentRespondId='" + commentRespondId + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", commentId='" + commentId + '\'' +
                '}';
    }

    public String getCommentRespondId() {
        return commentRespondId;
    }

    public void setCommentRespondId(String commentRespondId) {
        this.commentRespondId = commentRespondId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
