package com.example.androdid.entity;

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
    int pictureNumber;

    public PostContent(String contentId, String postId, String postContent, String postTitle, int pictureNumber) {
        this.contentId = contentId;
        this.postId = postId;
        this.postContent = postContent;
        this.postTitle = postTitle;
        this.pictureNumber = pictureNumber;
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

    public int getPictureNumber() {
        return pictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        this.pictureNumber = pictureNumber;
    }
}
