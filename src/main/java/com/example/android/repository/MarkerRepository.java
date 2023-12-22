package com.example.android.repository;

import com.example.android.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, String>, JpaSpecificationExecutor<Marker> {
    Marker findByLatitudeAndLongitude(double latitude, double longitude);
}
