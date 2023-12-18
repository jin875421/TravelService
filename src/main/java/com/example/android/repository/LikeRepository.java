package com.example.android.repository;

import com.example.android.entity.Like;
import com.example.android.entity.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, String>, JpaSpecificationExecutor<Like> {
    Star findByPostIdAndUserId(String postId, String userId);
    void deleteByPostIdAndUserId(String postId, String userId);

    @Query(value = "select post_id from star where user_id =?1",nativeQuery = true)
    List<String> findPostIdByUserId(String userId);
}
