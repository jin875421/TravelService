package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserExtraInfo {
    @Id
    private String userId;
    private String followGroupInfo;

    public UserExtraInfo() {

    }

    public UserExtraInfo(String userId, String followGroupInfo) {
        this.userId = userId;
        this.followGroupInfo = followGroupInfo;
    }


}
