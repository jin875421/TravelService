package com.example.android.repository;

import com.example.android.entity.RecoAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecoAttractionRepository extends JpaRepository<RecoAttraction, String>, JpaSpecificationExecutor<RecoAttraction> {

}
