package com.example.android.repository;

import com.example.android.entity.StrategyPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StrategyPictureRepository extends JpaRepository<StrategyPicture, String>, JpaSpecificationExecutor<StrategyPicture> {
    List<StrategyPicture> findByStrategyId(String StrategyId);
    @Query(value = "delete from strategy_picture where picture_path =?1",nativeQuery = true)
    @Transactional
    @Modifying
    void deleteByPicturePath(String picturePath);
}
