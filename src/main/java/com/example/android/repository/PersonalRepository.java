package com.example.android.repository;

import com.example.android.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonalRepository extends JpaRepository<Personal, String>, JpaSpecificationExecutor<Personal> {
    Personal findByUserId(String userId);
}
