package com.example.androdid.repository;

import com.example.androdid.entity.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostPictureRepository extends JpaRepository<PostPicture, String>, JpaSpecificationExecutor<PostPicture> {

    List<PostPicture> findByPostId(String postId);
}
