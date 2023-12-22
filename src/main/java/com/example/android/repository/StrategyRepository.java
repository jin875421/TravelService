package com.example.android.repository;

import com.example.android.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StrategyRepository extends JpaRepository<Strategy, String>, JpaSpecificationExecutor<Strategy> {
    List<Strategy> findAllByLatitudeAndLongitude(String latitude, String longitude);
}
