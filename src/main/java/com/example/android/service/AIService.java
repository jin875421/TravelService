package com.example.android.service;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.example.android.entity.AIResult;
import com.example.android.entity.ImageRecognition;
import com.example.android.entity.Result;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    //设置APPID/AK/SK
    public static final String APP_ID = "69943541";
    public static final String API_KEY = "jcrNKxCxETHRZfQajyKlzakx";
    public static final String SECRET_KEY = "scBMHOAPE0WjiQrklVCU8Pulsx5aUXdw";
    // 初始化一个AipImageClassify
    AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
    private ImageRecognition imageRecognition = new ImageRecognition();
    public Result getPattern(int sign, String path){
        if (sign == 1){
            return imageRecognition.getPattern1(client, path);
        }else if (sign == 2){
            return imageRecognition.getPattern2(client, path);
        }else if (sign == 3){
            return imageRecognition.getPattern3(client, path);
        }else if (sign == 4){
            return imageRecognition.getPattern4(client, path);
        }else if (sign == 5){
            return imageRecognition.getPattern5(client, path);
        }else if (sign == 6){
            return imageRecognition.getPattern6(client, path);
        }else if (sign == 7){
            return imageRecognition.getPattern7(client, path);
        }else if (sign == 8){
            return imageRecognition.getPattern8(client, path);
        }
        return null;
    }
}
