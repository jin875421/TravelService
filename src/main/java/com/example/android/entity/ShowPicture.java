package com.example.android.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

//这个类封装所有用户和对应的照片好照片对应日期的数据，用于返回给客户端来显示所有照片，这个实体类不对应表
@Data
public class ShowPicture {

//    private String pictureId;
//    private String userId;
    private List<String> picturePath;
    private Date travelDate;
    private String placeName;

}
