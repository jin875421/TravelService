package com.example.android.controller;

import com.aliyuncs.utils.StringUtils;
import com.example.android.entity.RecoAttraction;
import com.example.android.entity.RecoAttractionImg;
import com.example.android.service.RecoAttractionImgService;
import com.example.android.service.RecoAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/recoAttraction")
public class RecoAttractionController {
    public static List<RecoAttraction> dailyRecoAttractionList = new ArrayList<>();
    @Autowired
    private RecoAttractionService recoAttractionService;
    @Autowired
    private RecoAttractionImgService recoAttractionImgService;
    @GetMapping("/getRecoAttractionList")
    public List<RecoAttraction> getRecoAttractionList(){
        for (RecoAttraction r:dailyRecoAttractionList){
            if(StringUtils.isEmpty(r.getAttractionName())){
                r.setAttractionName("未知景点");
            }
            if(StringUtils.isEmpty(r.getCountry())){
                r.setCountry("");
            }
            if(StringUtils.isEmpty(r.getProvince())){
                r.setProvince("");
            }
            if(StringUtils.isEmpty(r.getCity())){
                r.setCity("");
            }
            if(StringUtils.isEmpty(r.getAddress())){
                r.setAddress("");
            }
            if(StringUtils.isEmpty(r.getAttractionDesc())){
                r.setAttractionDesc("暂无景点描述");
            }
        }
//        System.out.println(dailyRecoAttractionList);
        return dailyRecoAttractionList;
    }
    @GetMapping("/getAllRecoAttraction")
    public List<RecoAttraction> getAllRecoAttraction(){
        List<RecoAttraction> recoAttractionList = recoAttractionService.getRecoAttractionList();
        for (RecoAttraction r:recoAttractionList){
            if(StringUtils.isEmpty(r.getAttractionName())){
                r.setAttractionName("未知景点");
            }
            if(StringUtils.isEmpty(r.getCountry())){
                r.setCountry("");
            }
            if(StringUtils.isEmpty(r.getProvince())){
                r.setProvince("");
            }
            if(StringUtils.isEmpty(r.getCity())){
                r.setCity("");
            }
            if(StringUtils.isEmpty(r.getAddress())){
                r.setAddress("");
            }
            if(StringUtils.isEmpty(r.getAttractionDesc())){
                r.setAttractionDesc("暂无景点描述");
            }
        }
        return recoAttractionList;
    }
}
