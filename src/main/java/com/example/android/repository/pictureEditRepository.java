package com.example.android.repository;

import com.example.android.entity.PictureEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface pictureEditRepository extends JpaRepository<PictureEdit, String>, JpaSpecificationExecutor<PictureEdit> {
    PictureEdit findByUserId(String userId);

    List<PictureEdit> findPictureEditsByUserId(String userId);

    void deleteByPictureUrl(String pictureUrl);
}
