package com.example.android.repository;

import com.example.android.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface UserInfoRepository extends JpaRepository<UserInfo, String>, JpaSpecificationExecutor<UserInfo> {
    UserInfo findByUserIdAndPassword(String userId, String password);

    UserInfo findByEmail(String email);

    UserInfo findByUserId(String userId);

    UserInfo findByUserPhoneNumber(String phone);

    // 在数据库中模糊查询用户名带有目标字符串的用户
    List<UserInfo> findByUserNameContaining(String userName);
}