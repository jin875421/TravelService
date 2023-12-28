package com.example.android.controller;


import com.example.android.entity.ShowPicture;
import com.example.android.entity.ShowTravel;
import com.example.android.entity.TravelRecord;
import com.example.android.service.TravelsService;
import com.example.android.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/travel")
public class TravelController {
    //首先依赖注入
    @Autowired
    private TravelsService travelsService;

    //这个方法用于实现根据用户传递过来的id来查找到所有的照片地址和相应的日期，然后返回成一个list列表
    @PostMapping("/showPictures")
    public List<ShowPicture> showPictures(String userId){
        List<ShowPicture> list = new ArrayList<>();
        list = travelsService.getPicturesShowedByUserId(userId);
        return list;

    }
    //这个方法用于新增旅游记录信息，用于将一次传过来的包含在一次旅程中的地点一个地点记录保存到数据库中

    private String uploadDirectory = "D:\\Upload\\travelpictures\\";
    @PostMapping("/createTravelRecord")
    //旅游信息上传
    public String handleFileUpload(
            @RequestPart("travelrecord") TravelRecord travelRecord,
            @RequestParam("images") List<MultipartFile> files,
            @RequestParam("identifiers") List<String> identifiers,
            @RequestParam("sequenceNumbers") List<Integer> sequenceNumbers,
            @RequestParam("totalChunks") List<Integer> totalChunks
    ) {

        travelRecord.setPlaceId(UUID.randomUUID().toString());
        //如果图片存在
        if (travelRecord.getPlaceId() != null){
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
                        fileNames.add("travelpictures/"+combinedFileName);
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

            //现在已经将图片保存好了，并且图片名称是fileNames列表，现在要将这些数据放到数据库中
            travelRecord.setImage(fileNames);

        }

        //现在将数据放到数据库中
        travelsService.createTravelRecoed(travelRecord);
        return "success"; // 上传成功
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
    @GetMapping("/showATravel")
    public List<TravelRecord> showATravel(String travelId){
        return travelsService.showATravel(travelId);
    }
    @GetMapping("/getReview")
    public List<ShowTravel> showTravelList(String userId){
        return travelsService.listTravels(userId);
    };

    @Transactional
    @GetMapping("/deleteTravel")
    public void deleteTravel(String travelId){
        travelsService.deleteTravel(travelId);
    }
//    @Transactional
//    @PostMapping("/EditTravelRecord")
//    //旅游信息上传
//    public void EditTravel(
//            @RequestPart("travelrecord") TravelRecord travelRecord,
//            @RequestParam("images") List<MultipartFile> files,
//            @RequestParam("identifiers") List<String> identifiers,
//            @RequestParam("sequenceNumbers") List<Integer> sequenceNumbers,
//            @RequestParam("totalChunks") List<Integer> totalChunks
//    ) {
//        //先执行删除，在创建
//        travelsService.deleteTravel(travelRecord.getTravelId());
//        travelRecord.setPlaceId(UUID.randomUUID().toString());
//        //如果图片存在
//        if (travelRecord.getPlaceId() != null){
//            List<String> fileNames = new ArrayList<>();
//            for (int i = 0; i < files.size(); i++) {
//                MultipartFile file = files.get(i);
//                String identifier = identifiers.get(i);
//                int sequenceNumber = sequenceNumbers.get(i);
//                int totalChunk = totalChunks.get(i);
//                try {
//                    // 创建目录（如果不存在）
//                    File uploadDir = new File(uploadDirectory);
//                    if (!uploadDir.exists()) {
//                        uploadDir.mkdirs();
//                    }
//                    // 构建文件名（使用标识符和序号）
//                    String fileName = identifier + "_" + sequenceNumber + "_" + file.getOriginalFilename();
//                    // 保存文件分片
//                    byte[] bytes = file.getBytes();
//                    Path path = Paths.get(uploadDirectory + fileName);
//                    Files.write(path, bytes);
//                    // 检查是否所有分片都已上传
//                    if (sequenceNumber == totalChunk - 1) {
//                        // 如果所有分片都已上传，则重组文件
//                        String combinedFileName = identifier + "_" + file.getOriginalFilename();
//                        fileNames.add("travelpictures/"+combinedFileName);
//                        File combinedFile = new File(uploadDirectory + combinedFileName);
//                        for (int j = 0; j < totalChunk; j++) {
//                            File partFile = new File(uploadDirectory + identifier + "_" + j + "_" + file.getOriginalFilename());
//                            FileOutputStream fos = new FileOutputStream(combinedFile, true);
//                            FileInputStream fis = new FileInputStream(partFile);
//                            FileCopyUtils.copy(fis, fos);
//                            fis.close();
//                            fos.close();
//                            partFile.delete(); // 清理分片文件
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            //现在已经将图片保存好了，并且图片名称是fileNames列表，现在要将这些数据放到数据库中
//            travelRecord.setImage(fileNames);
//        }
//
//        //现在将数据放到数据库中
//        travelsService.createTravelRecoed(travelRecord);
//    }


}






