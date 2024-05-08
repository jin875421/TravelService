package com.example.android.repository;

import com.example.android.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

//这个持久层接口用于操作travels表
public interface TravelRepository extends JpaRepository<Travel, String>, JpaSpecificationExecutor<Travel> {


    public List<Travel> findTravelsByUserId(String userId);

    public Travel findTravelsByTravelId(String travelId);

    String findTravelNameByTravelId(String travelId);

}
