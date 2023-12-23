package com.example.android.controller;

import com.example.android.entity.RecoAttraction;
import com.example.android.entity.RecoAttractionImg;
import com.example.android.service.RecoAttractionImgService;
import com.example.android.service.RecoAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    static List<RecoAttraction> dailyRecoAttractionList = new ArrayList<>();
    @Autowired
    private RecoAttractionService recoAttractionService;
    @Autowired
    private RecoAttractionImgService recoAttractionImgService;
    static boolean todayFirstGet = true;
    @GetMapping("/getRecoAttractionList")
    public List<RecoAttraction> getRecoAttractionList(){
        if (todayFirstGet){
            List<RecoAttraction> recoAttractionList = recoAttractionService.getRecoAttractionList();
            System.out.println(recoAttractionList);
            int [] randomArray = new int[2];
            // 使用循环生成随机数并放入数组
            for (int i = 0; i < 2; i++) {
                int num = ThreadLocalRandom.current().nextInt(0, recoAttractionList.size());
                // 使用循环检查随机数是否已经存在于数组中
                boolean contains = false;
                for (int j = 0; j < i; j++) {
                    if (randomArray[j] == num) {
                        contains = true;
                        break;
                    }
                }
                // 如果随机数已经存在于数组中，则重新生成
                if (contains) {
                    i--;
                } else {
                    randomArray[i] = num;
                    System.out.println(randomArray[i]);
                }
            }
            for (int i:randomArray){
                dailyRecoAttractionList.add(recoAttractionList.get(i));
            }
            todayFirstGet = false;
        }
        System.out.println(dailyRecoAttractionList);
        return dailyRecoAttractionList;
    }
}
