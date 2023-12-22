package com.example.android.repository;

import com.example.android.entity.RecoAttractionImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.List;

public interface RecoAttractionImgRepository extends JpaRepository<RecoAttractionImg, String>, JpaSpecificationExecutor<RecoAttractionImg> {
    @Transactional
    public void deleteByAttractionId(String attractionId);

    List<RecoAttractionImg> findByAttractionId(String attractionId);
}
