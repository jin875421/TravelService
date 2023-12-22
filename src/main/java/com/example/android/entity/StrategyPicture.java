package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StrategyPicture {
    @Id
    private String pictureId;
    private String strategyId;
    private String picturePath;

    @Override
    public String toString() {
        return "StrategyPicture{" +
                "pictureId='" + pictureId + '\'' +
                ", strategyId='" + strategyId + '\'' +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }

    public StrategyPicture() {
    }

    public StrategyPicture(String pictureId, String strategyId, String picturePath) {
        this.pictureId = pictureId;
        this.strategyId = strategyId;
        this.picturePath = picturePath;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
