package com.example.android.controller;

import com.example.android.entity.Marker;
import com.example.android.entity.PostItem;
import com.example.android.entity.ReturnStrategy;
import com.example.android.entity.Strategy;
import com.example.android.service.CoverUploadService;
import com.example.android.service.PostService;
import com.example.android.service.StrategyService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/strategy")
public class StrategyController {
    @Autowired
    StrategyService strategyService;
    @Autowired
    private PostService postService;
    @Autowired
    private CoverUploadService coverUploadService;
    private String uploadDirectory = "D:\\Upload\\strategy\\";

    @PostMapping("/getStrategy")
    public String getStrategy(@RequestParam String latitude, @RequestParam String longitude) {
        Gson gson = new Gson();
        List<ReturnStrategy> strategyList = strategyService.getStrategy(latitude, longitude);
        String json = gson.toJson(strategyList);
        System.out.println("通过经纬度获取的strategy集合"+json);
        return json;
    }

    @PostMapping("/getStrategyById")
    public String getStrategyById(@RequestBody String strategyId) {
        System.out.println("getStrategyById启动");
        Gson gson = new Gson();
        ReturnStrategy strategy = strategyService.getStrategyById(strategyId);
        String json = gson.toJson(strategy);
        System.out.println("通过strategyId找到的信息"+json);
        return json;
    }

    @GetMapping("/getIconMap")
    public String getIcon() {
        System.out.println("getIcon启动");
        Gson gson = new Gson();
        HashMap<String, String> iconMap;
        iconMap = strategyService.getIconMap();
        String json = gson.toJson(iconMap);
        System.out.println("通过strategyId找到的iconMap"+json);
        return json;
    }

    @PostMapping("/addStrategy")
    public void addStrategy(@RequestParam String strategyId,
                            @RequestParam String userId,
                            @RequestParam String title,
                            @RequestParam String describe,
                            @RequestParam String latitude,
                            @RequestParam String longitude,
                            @RequestParam String selectedCity,
                            @RequestParam String time,
                            @RequestPart("pictureNum") int pictureNum,
                            @RequestParam("identifiers") List<String> identifiers,
                            @RequestParam("sequenceNumbers") List<Integer> sequenceNumbers,
                            @RequestParam("totalChunks") List<Integer> totalChunks,
                            @RequestParam("images") List<MultipartFile> files
    ) {
        System.out.println("addStrategy启动");
        //保存marker
        Marker marker = new Marker(strategyId, selectedCity, Double.parseDouble(latitude), Double.parseDouble(longitude));
        strategyService.addMarker(marker);
        String coverPath;
        Strategy strategy = new Strategy(strategyId, userId, title, describe, latitude, longitude, time);
        System.out.println("==="+strategy.toString()+"===");
        strategyService.addStrategy(strategy);
        //如果图片存在
        List<String> picturePath = new ArrayList<>();
        if (pictureNum>0){
            List<String> fileNames = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String identifier = identifiers.get(i);
                int sequenceNumber = sequenceNumbers.get(i);
                int totalChunk = totalChunks.get(i);
                try {
                    // 创建目录（如果不存在）
                    File uploadDir = new File(uploadDirectory);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    // 构建文件名（使用标识符和序号）
                    String fileName = identifier + "_" + sequenceNumber + "_" + file.getOriginalFilename();
                    // 保存文件分片
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadDirectory + fileName);
                    Files.write(path, bytes);
                    // 检查是否所有分片都已上传
                    if (sequenceNumber == totalChunk - 1) {
                        // 如果所有分片都已上传，则重组文件
                        String combinedFileName = identifier + "_" + file.getOriginalFilename();
                        fileNames.add("strategy/"+combinedFileName);
                        File combinedFile = new File(uploadDirectory + combinedFileName);
                        for (int j = 0; j < totalChunk; j++) {
                            File partFile = new File(uploadDirectory + identifier + "_" + j + "_" + file.getOriginalFilename());
                            FileOutputStream fos = new FileOutputStream(combinedFile, true);
                            FileInputStream fis = new FileInputStream(partFile);
                            FileCopyUtils.copy(fis, fos);
                            fis.close();
                            fos.close();
                            partFile.delete(); // 清理分片文件
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // 处理异常
                    throw new RuntimeException("文件分片上传失败.");
                }
            }
            picturePath = fileNames;
        }
        strategyService.saveStrategyPicture(strategyId, picturePath);
    }

    @GetMapping("/getAllMarker")
    public String getAllMarker() {
        Gson gson = new Gson();
        List<Marker> markerList = strategyService.getMarker();
        String json = gson.toJson(markerList);
        return json;
    }
}
