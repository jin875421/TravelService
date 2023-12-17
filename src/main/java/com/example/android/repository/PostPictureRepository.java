package com.example.android.repository;

import com.example.android.entity.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PostPictureRepository extends JpaRepository<PostPicture, String>, JpaSpecificationExecutor<PostPicture> {

    List<PostPicture> findByPostId(String postId);
    @Query(value = "delete from post_picture where picture_path =?1",nativeQuery = true)
    @Transactional
    @Modifying
    void deleteByPicturePath(String picturePath);
}
