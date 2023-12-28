package com.example.android.repository;

import com.example.android.entity.Comment;
import com.example.android.entity.StrategyComment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StrategyCommentRepository extends JpaRepository<StrategyComment, String>, JpaSpecificationExecutor<StrategyComment> {
    List<StrategyComment> findByStrategyId(String StrategyId, Sort sort);
    List<StrategyComment> findByParentId(String parentId, Sort sort);
    void deleteByStrategyId(String StrategyId);
}
