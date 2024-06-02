package com.example.android.repository;

import com.example.android.entity.UserExtraInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserExtraInfoRepository extends JpaRepository<UserExtraInfo, String>, JpaSpecificationExecutor<UserExtraInfo> {
    UserExtraInfo findByUserId(String userId);
}
