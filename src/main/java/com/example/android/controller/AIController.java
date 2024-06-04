package com.example.android.controller;

import com.example.android.entity.*;
import com.example.android.service.AIService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping("/AI")
public class AIController {
    @Autowired
    private AIService aiService;

    private String uploadDirectory =  "D:\\Upload\\AI\\";

    @PostMapping("/recognition")
    //帖子上传
    public Result handleFileUpload(
            @RequestPart("post") PostItem post,
            @RequestParam("identifiers") List<String> identifiers,
            @RequestParam("sequenceNumbers") List<Integer> sequenceNumbers,
            @RequestParam("totalChunks") List<Integer> totalChunks,
            @RequestParam("images") List<MultipartFile> files

    ) {
        //如果图片存在
        if (post.getPictureNumber()>0){
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
                    String filename = file.getOriginalFilename();
                    String extension = "";
                    int lastDotIndex = filename.lastIndexOf(".");
                    if (lastDotIndex > 0) {
                        extension = filename.substring(lastDotIndex + 1);
                    }
                    String fileName = identifier + "_" + sequenceNumber + "_" + post.getUserId()+"."+extension;
                    // 保存文件分片
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadDirectory + fileName);
                    Files.write(path, bytes);
                    // 检查是否所有分片都已上传
                    if (sequenceNumber == totalChunk - 1) {
                        // 如果所有分片都已上传，则重组文件
                        String combinedFileName = identifier + "_" + post.getUserId()+"."+extension;
                        fileNames.add("AI/"+combinedFileName);
                        File combinedFile = new File(uploadDirectory + combinedFileName);
                        for (int j = 0; j < totalChunk; j++) {
                            File partFile = new File(uploadDirectory + identifier + "_" + j + "_" + post.getUserId()+"."+extension);
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
                }
            }
            String myPath = "D:/Upload/"+fileNames.get(0);
            int sign=0;
            System.out.println("post="+post.getPostTitle());
            if(post.getPostTitle().equals("1")){
                sign = 1;
            }else if(post.getPostTitle().equals("2")){
                sign = 2;
            }else if(post.getPostTitle().equals("3")){
                sign = 3;
            }else if(post.getPostTitle().equals("4")){
                sign = 4;
            }else if(post.getPostTitle().equals("5")){
                sign = 5;
            }else if(post.getPostTitle().equals("6")){
                sign = 6;
            }else if(post.getPostTitle().equals("7")){
                sign = 7;
            }else if(post.getPostTitle().equals("8")){
                sign = 8;
            }else{
                sign = 1;
            }
            System.out.println("sign="+sign);
            System.out.println("路径"+myPath);
            Result aiResult = aiService.getPattern(sign,myPath);

            return aiResult;
        }
        return null;
    }
    @PostMapping("/addSpeech")
    public void addSpeech(
            @RequestParam String userId,
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String text
    ){
        System.out.println("接收语音内容启动");
        Speech speech = new Speech(text,userId,Double.parseDouble(latitude),Double.parseDouble(longitude));
        aiService.addSpeech(speech);
    }

    @GetMapping("/getSpeech")
    public String getSpeech(){
        System.out.println("获取语音内容启动");
        Gson gson = new Gson();
        String json = gson.toJson(aiService.getSpeech());
        return json;
    }
}
