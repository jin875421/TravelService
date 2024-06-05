package com.example.android.repository;

import com.example.android.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, String>, JpaSpecificationExecutor<Follow> {
    List<Follow> findByUserId(String userId);

    void deleteById(String id);

    void deleteByUserIdAndFollowId(String userId, String followId);

    Follow findByUserIdAndFollowId(String userId, String followId);

    void deleteByFollowId(String followId);

    // 更新 （直接用save方法？）
    @Transactional
    @Modifying
    @Query("UPDATE Follow f " +
            "SET f.groupOf = :#{#follow.groupOf} " +
            "WHERE f.userId = :#{#follow.userId} AND f.followId = :#{#follow.followId}")
    int updateFollow(Follow follow);
}
