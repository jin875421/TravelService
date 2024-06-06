package com.example.android.repository;

import com.example.android.entity.PictureEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface pictureEditRepository extends JpaRepository<PictureEdit, String>, JpaSpecificationExecutor<PictureEdit> {
    PictureEdit findByUserId(String userId);
}
