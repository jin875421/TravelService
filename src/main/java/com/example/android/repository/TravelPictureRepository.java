package com.example.android.repository;

import com.example.android.entity.TravelPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TravelPictureRepository extends JpaRepository<TravelPicture, String>, JpaSpecificationExecutor<TravelPicture> {

    //在这里写一个方法，传入placeId，查找到所有的TravelPicture
    public List<TravelPicture> findTravelPicturesByPlaceId(String placeId);

    void deleteByPlaceId(String s);

    void deleteByPicturePath(String path);
    //在这里写一个方法，通过找到的

}
