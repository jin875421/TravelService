package com.example.androdid.entity;

public class PostWithUserInfo {
    private PostItem post;
    private UserInfo userInfo;

    public PostItem getPost() {
        return post;
    }

    public void setPost(PostItem post) {
        this.post = post;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public PostWithUserInfo(PostItem post,UserInfo userInfo){
        this.post = post;
        this.userInfo = userInfo;
    }
}
