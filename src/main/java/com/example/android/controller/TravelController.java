package com.example.android.controller;


import com.example.android.entity.ShowPicture;
import com.example.android.entity.ShowTravel;
import com.example.android.entity.TravelRecord;
import com.example.android.service.TravelsService;
import com.example.android.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/travel")
public class TravelController {

    //这是一个全局变量ip地址
    String ip = "192.168.43.75:8080";


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
    private  String imageUploadDirectory="D:\\Upload\\";

    //这个方法用于添加旅游信息
    @PostMapping("/createTravelRecoedTest")
    public void text(@RequestBody TravelRecord travelRecord){
        travelsService.createTravelRecoed(travelRecord);
    }

    @PostMapping("/uploadphoto")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("placeId") String placeId
                                                   ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        try {
            // 保存文件到指定路径
            String fileName = file.getOriginalFilename();
            String filePath =  uploadDirectory+fileName;
            file.transferTo(new File(filePath));
            travelsService.savepicture("travelpictures/"+fileName,placeId);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
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
    public List<ShowTravel> showTravelList(String userId) throws ParseException {
        return travelsService.listTravels(userId);
    };

    @Transactional
    @GetMapping("/deleteTravel")
    public void deleteTravel(String travelId){
        travelsService.deleteTravel(travelId);
    }
    @Transactional
    @PostMapping("/deletePicture")
    public ResponseEntity<String> deletePicture(@RequestBody String path) {
        try {
            // 确保逻辑路径符合预期格式，防止路径遍历攻击
            System.out.println("sdasd"+path);
//            if (!path.matches("^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)?$")) {
//                return ResponseEntity.badRequest().body("无效的图片路径");
//            }

            // 构建服务器上的实际文件路径，确保路径安全
            String absolutePath = Paths.get(imageUploadDirectory, path).toString();

            // 验证文件是否存在且是文件
            if (Files.exists(Paths.get(absolutePath)) && Files.isRegularFile(Paths.get(absolutePath))) {
                travelsService.deletePicture(path);
                Files.delete(Paths.get(absolutePath)); // 删除文件
                return ResponseEntity.ok("图片已成功删除");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // 记录日志并返回错误信息
            return ResponseEntity.status(500).body("图片删除过程中发生错误");
        }


    }
}






