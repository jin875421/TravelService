package com.example.android.repository;

import com.example.android.entity.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SpeechRepository extends JpaRepository<Speech, String>, JpaSpecificationExecutor<Speech> {
    List<Speech> findAll();
}
