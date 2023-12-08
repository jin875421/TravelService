package com.example.android.repository;

import com.example.android.entity.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StarRepository extends JpaRepository<Star, String>, JpaSpecificationExecutor<Star> {

    Star findByPostIdAndUserId(String postId,String userId);
}
