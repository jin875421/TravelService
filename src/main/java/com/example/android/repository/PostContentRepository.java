package com.example.android.repository;

import com.example.android.entity.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostContentRepository extends JpaRepository<PostContent, String>, JpaSpecificationExecutor<PostContent> {
    PostContent findByPostId(String postId);
}
