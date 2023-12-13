package com.example.android.repository;

import com.example.android.entity.TravelPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TravelPlaceRepository extends JpaRepository<TravelPlace, String>, JpaSpecificationExecutor<TravelPlace> {

    List<TravelPlace> findTravelPlaceByTravelIdIn(List<String> travelIds);

    //通过travelId找到所有的travelPlace
    List<TravelPlace> findTravelPlacesByTravelId(String travelId);
}
