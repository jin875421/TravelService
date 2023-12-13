package com.example.android.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

//这个类用于显示所有的旅行信息，用于在点击旅行回顾的时候使用
@Data
public class ShowTravel {
    private String travelId;
    private Date date;
    private List<String> images;
}
