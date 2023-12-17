package com.example.android.repository;

import com.example.android.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, String>, JpaSpecificationExecutor<Post> {
    @Transactional
    void deleteByUserId(String userId);
}
