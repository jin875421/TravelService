package com.example.android.entity;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.HashMap;

//1.通用 2.菜品 3.logo 4.动物 5.植物 6.地标 7.果蔬 8.货币
public class ImageRecognition {
    //设置APPID/AK/SK
    public static final String APP_ID = "69943541";
    public static final String API_KEY = "jcrNKxCxETHRZfQajyKlzakx";
    public static final String SECRET_KEY = "scBMHOAPE0WjiQrklVCU8Pulsx5aUXdw";

    //通用
    public Result getPattern1(AipImageClassify client, String path) {
        System.out.println("识别通用");
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", "1");


        // 参数为本地路径
        String image = path;
        JSONObject res = client.advancedGeneral(image, options);
        System.out.println("转换前res="+res.toString());
        Gson gson = new Gson();
        Result[] result = gson.fromJson(res.getJSONArray("result").toString(), Result[].class);

        return result[0];
    }
    //菜品
    public Result getPattern2(AipImageClassify client, String path) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "3");
        options.put("filter_threshold", "0.7");
        options.put("baike_num", "5");


        // 参数为本地路径
        String image = path;
        JSONObject res = client.advancedGeneral(image, options);
        System.out.println("ImageR="+res.toString());
        Gson gson = new Gson();
        Result[] result = gson.fromJson(res.getJSONArray("result").toString(), Result[].class);

        return result[0];
    }

    //logo
    public Result getPattern3(AipImageClassify client, String path) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("custom_lib", "false");


        // 参数为本地路径
        String image = path;
        JSONObject res = client.advancedGeneral(image, options);
        System.out.println("Image="+res.toString());
        Gson gson = new Gson();
        Result[] result = gson.fromJson(res.getJSONArray("result").toString(), Result[].class);

        return result[0];
    }

    //动物
    public Result getPattern4(AipImageClassify client, String path) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "3");
        options.put("baike_num", "5");


        // 参数为本地路径
        String image = path;
        JSONObject res = client.advancedGeneral(image, options);
        System.out.println("ImageR="+res.toString());
        Gson gson = new Gson();
        Result[] result = gson.fromJson(res.getJSONArray("result").toString(), Result[].class);

        return result[0];
    }

    //植物
    public Result getPattern5(AipImageClassify client, String path) {
        System.out.println("识别植物");
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", "5");


        // 参数为本地路径
        String image = path;
        JSONObject res = client.advancedGeneral(image, options);
        System.out.println("ImageR="+res.toString());
        Gson gson = new Gson();
        Result[] result = gson.fromJson(res.getJSONArray("result").toString(), Result[].class);

        return result[0];
    }

    //地标
    public Result getPattern6(AipImageClassify client, String path) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();


        // 参数为本地路径
        String image = path;
        JSONObject res = client.advancedGeneral(image, options);
        System.out.println("ImageR="+res.toString());
        Gson gson = new Gson();
        Result[] result = gson.fromJson(res.getJSONArray("result").toString(), Result[].class);

        return result[0];
    }

    //果蔬
    public Result getPattern7(AipImageClassify client, String path) {
        /// 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "3");


        // 参数为本地路径
        String image = path;
        JSONObject res = client.advancedGeneral(image, options);
        System.out.println("ImageR="+res.toString());
        Gson gson = new Gson();
        Result[] result = gson.fromJson(res.getJSONArray("result").toString(), Result[].class);

        return result[0];
    }

    //货币
    public Result getPattern8(AipImageClassify client, String path) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();


        // 参数为本地路径
        String image = path;
        JSONObject res = client.advancedGeneral(image, options);
        System.out.println("ImageR="+res.toString());
        Gson gson = new Gson();
        Result[] result = gson.fromJson(res.getJSONArray("result").toString(), Result[].class);

        return result[0];
    }
}
