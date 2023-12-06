package com.example.androdid.repository;

import com.example.androdid.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserInfoRepository extends JpaRepository<UserInfo, String>, JpaSpecificationExecutor<UserInfo> {
    UserInfo findByUserIdAndPassword(String userId, String password);

    UserInfo findByEmail(String email);

    UserInfo findByUserId(String userId);

    UserInfo findByUserPhoneNumber(String phone);
}