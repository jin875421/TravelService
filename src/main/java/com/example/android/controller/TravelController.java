package com.example.android.controller;


import com.example.android.entity.ShowPicture;
import com.example.android.entity.ShowTravel;
import com.example.android.entity.TravelRecord;
import com.example.android.service.TravelsService;
import com.example.android.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travel")
public class TravelController {
    //首先依赖注入
    @Autowired
    private TravelsService travelsService;
    //这个方法用于实现根据用户传递过来的id来查找到所有的照片地址和相应的日期，然后返回成一个list列表
    @PostMapping("/showPictures")
    public Result<List<ShowPicture>> showPictures(String userId){
        Result<List<ShowPicture>> result = new Result<List<ShowPicture>>();
        try{
            //这里是查找成功的情况
            //这里调用service方法，通过id找到包含照片信息和对应日期的list列表
            List<ShowPicture>  list = travelsService.getPicturesShowedByUserId(userId);
            result.setData(list);
            result.setCode("200");
            result.setMsg("照片查找成功");
        }catch (Exception e){
            result.setData(null);
            result.setCode("500");
            result.setMsg("查找失败");
        }
        return result;
    }
    //这个方法用于新增旅游记录信息，用于将一次传过来的包含在一次旅程中的地点一个地点记录保存到数据库中
    //TODO 这个方法需要修改，还没有完成！！！！！！！！！！！！！！！！！！需要和前端协调一些东西
    @PostMapping("/createTravelRecoed")
    public int createTravelRecoed(@RequestBody TravelRecord travelRecord){
        int result = travelsService.createTravelRecoed(travelRecord);        //TODO 定义返回信息的实体类，和前端协调0
        return result;
    }

    //这个方法用于实现收到userId，返回所有的旅行信息的功能
    @PostMapping("/showTravels")
    public Result<List<ShowTravel>> showTravels(String userId){
        Result<List<ShowTravel>> result = new Result<>();
        //在service层中实现
        try{
            List<ShowTravel> showTravels = travelsService.showTravels(userId);
            result.setData(showTravels);
            result.setCode("200");
            result.setMsg("查找成功");
        }catch (Exception e){
            result.setData(null);
            result.setCode("500");
            result.setMsg("查找失败");
        }
        return result;
    }

    //这个方法用于实现将对应旅游的信息传递到前端，接收到travelId，返回所有的该次旅游的数据
    @PostMapping("/showATravel")
    public Result<List<TravelRecord>> showATravel(String travelId){
        Result<List<TravelRecord>> result = new Result<>();
        try{
            List<TravelRecord> travelRecords = travelsService.showATravel(travelId);
            result.setData(travelRecords);
            result.setCode("200");
            result.setMsg("查找成功");
        }catch(Exception e){
            result.setData(null);
            result.setCode("500");
            result.setMsg("查找失败");
        }
        return result;
    }





}






