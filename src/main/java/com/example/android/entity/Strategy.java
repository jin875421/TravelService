package com.example.android.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Strategy {
    @Id
    private String strategyId;
    private String title;
    private String detail;
    private String latitude;
    private String longitude;
    private String time;
    private String userId;

    public Strategy(String strategyId, String userId, String title, String detail, String latitude, String longitude, String time) {
        this.strategyId = strategyId;
        this.userId = userId;
        this.title = title;
        this.detail = detail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    @Override
    public String toString() {
        return "StrategyController{" +
                "strategyId='" + strategyId + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + detail + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Strategy() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDescribe(String describe) {
        this.detail = describe;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
