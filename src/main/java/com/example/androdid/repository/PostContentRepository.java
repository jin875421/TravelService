package com.example.androdid.repository;

import com.example.androdid.entity.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostContentRepository extends JpaRepository<PostContent, String>, JpaSpecificationExecutor<PostContent> {
    PostContent findByPostId(String postId);
}
