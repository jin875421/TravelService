package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
@Entity
@Data
public class UserInfo {
    @Id
    @NotEmpty(message = "用户名不能为空")
    private String userId;
    @NotEmpty(message = "密码不能为空")
    private String password;
    private String avatar;
    private String email;
    private String userName;
    private String userPhoneNumber;
    private String sex;
    @Transient
    private String code;
    @Transient
    private String status;

    public UserInfo(){

    }
    public UserInfo(String userId){
        this.userId = userId;
    }
}
