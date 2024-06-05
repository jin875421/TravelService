package com.example.android.repository;

import com.example.android.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, String>, JpaSpecificationExecutor<Post> {
    @Transactional
    void deleteByUserId(String userId);
    List<Post> findByUserId(String userId);
    Page<Post> findAll(Pageable page);

}
