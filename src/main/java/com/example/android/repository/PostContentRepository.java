package com.example.android.repository;

import com.example.android.entity.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PostContentRepository extends JpaRepository<PostContent, String>, JpaSpecificationExecutor<PostContent> {
    PostContent findByPostId(String postId);
    void deleteByPostId(String postId);
    @Query(value = "update post_content set post_content =?1,post_title =?2 where post_id =?3",nativeQuery = true)
    void updateByPostId(String postId,String postContent,String postTitle);
}
