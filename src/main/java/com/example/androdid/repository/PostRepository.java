package com.example.androdid.repository;

import com.example.androdid.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String>, JpaSpecificationExecutor<Post> {
}
