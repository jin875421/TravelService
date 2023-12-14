package com.example.android.repository;

import com.example.android.entity.CommentRespond;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRespondRepository extends JpaRepository<CommentRespond, String>, JpaSpecificationExecutor<CommentRespond> {
    List<CommentRespond> findByCommentId(String commentId, Sort sort);
}
