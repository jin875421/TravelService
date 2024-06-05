package com.example.android.repository;

import com.example.android.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, String>, JpaSpecificationExecutor<Follow> {
    List<Follow> findByUserId(String userId);
    void deleteById(String id);

    void deleteByUserIdAndFollowId(String userId, String followId);

    Follow findByUserIdAndFollowId(String userId, String followId);

    void deleteByFollowId(String followId);

    List<Follow> findAllByFollowId(String followId);
}
