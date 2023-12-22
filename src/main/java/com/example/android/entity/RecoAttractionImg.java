package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tra_reco_attraction_img")
@Entity
@Data
public class RecoAttractionImg {
    /**
     * 图片ID
     */
    @Id
    private String imgId;

    /**
     * 地点ID
     */
    private String attractionId;

    /**
     * 图片路径
     */
    private String imgUrl;

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgId() {
        return imgId;
    }

    public void setAttractionId(String attractionId) {
        this.attractionId = attractionId;
    }

    public String getAttractionId() {
        return attractionId;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String toString() {
        return "RecoAttractionImg{" +
                "imgId='" + imgId + '\'' +
                ", attractionId='" + attractionId + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
