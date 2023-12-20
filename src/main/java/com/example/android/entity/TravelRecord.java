package com.example.android.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

//这个实体类是用于新增数据和添加数据的时候
@Data
public class TravelRecord {

    private String userId;//用户id
    private String placeId;//位置id
    private String travelId;//整个旅程的id
    private String placeName;//小的名称，也就是标题
    private String travelName;//大的名称
    private List<String> image;//旅行的图片
    private String content;//包含的内容
    private Date date;//日期


    //以下是对该类进行操作的思路
    /*
    说白了就是，将这个类中的数据放到对应的三张表对应的中，然后再通过springJPA来将三种类的对象中的数据存放到表中
    首先，通过UUID来建立一个travel_id，然后，直接封装数据
    * */


}
