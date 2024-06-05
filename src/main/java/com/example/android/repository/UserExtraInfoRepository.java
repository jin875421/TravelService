package com.example.android.repository;

import com.example.android.entity.UserExtraInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserExtraInfoRepository extends JpaRepository<UserExtraInfo, String>, JpaSpecificationExecutor<UserExtraInfo> {
    UserExtraInfo findByUserId(String userId);

    @Transactional
    @Modifying
    @Query("UPDATE UserExtraInfo u " +
            "SET u.followGroupInfo = :#{#userExtraInfo.followGroupInfo} " +
            "WHERE u.userId = :#{#userExtraInfo.userId}")
    int updateUserExtraInfo(UserExtraInfo userExtraInfo);
}
