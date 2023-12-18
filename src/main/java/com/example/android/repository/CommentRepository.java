package com.example.android.repository;

import com.example.android.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String>, JpaSpecificationExecutor<Comment> {
    List<Comment> findByPostId(String postId, Sort sort);
    List<Comment> findByParentId(String parentId, Sort sort);
    void deleteByPostId(String postId);
}
