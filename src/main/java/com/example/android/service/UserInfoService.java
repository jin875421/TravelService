package com.example.android.service;

import com.example.android.entity.UserInfo;
import com.example.android.repository.UserInfoRepository;
import com.example.android.utils.UniqueRandomCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository repository;
    public UserInfo login(String userId, String password) {
        UserInfo userInfo = repository.findByUserIdAndPassword(userId,password);
        return userInfo;
    }
    public UserInfo getUserInfo(String userId) {
        UserInfo userInfo = repository.findByUserId(userId);
        return userInfo;
    }
    public UserInfo addUserInfo(UserInfo userInfo) {
        String nameNum= UniqueRandomCodeGenerator.generateUniqueRandomCode();
        if(userInfo.getUserName()==null||userInfo.getUserName().isEmpty()){
            userInfo.setUserName("用户"+nameNum);
        }
        UserInfo save = repository.save(userInfo);
        return save;
    }

    public UserInfo forgotPassword(String email) {
        UserInfo userInfo=repository.findByEmail(email);
        return userInfo;
    }
    public UserInfo update(UserInfo user){
        UserInfo userInfo=repository.save(user);
        return userInfo;
    }
    public UserInfo emailLogin(String email){
        UserInfo userInfo=repository.findByEmail(email);
        return userInfo;
    }
    public UserInfo phoneLogin(String phone){
        UserInfo userInfo=repository.findByUserPhoneNumber(phone);
        return userInfo;
    }
    public UserInfo findByUserId(String userId){
        UserInfo userInfo=repository.findByUserId(userId);
        return userInfo;
    }


}
