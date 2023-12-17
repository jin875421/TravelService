package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class PostContent {
    @Id
    String contentId;
    String postId;
    String postContent;
    String postTitle;

    public PostContent(String contentId, String postId, String postContent, String postTitle) {
        this.contentId = contentId;
        this.postId = postId;
        this.postContent = postContent;
        this.postTitle = postTitle;
    }

    public PostContent() {

    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

}
