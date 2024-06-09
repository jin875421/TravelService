package com.example.android.controller;

import com.example.android.entity.Personal;
import com.example.android.entity.PictureEdit;
import com.example.android.service.pictureEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pictureEdit")
public class pictureEditController {
    @Autowired
    private pictureEditService pictureEditService;
    private String uploadDirectorys = "D:\\Upload\\pictureEdit\\";
    @GetMapping("/getPicture")
    public PictureEdit getPicture(@RequestParam("userId") String userId) {
        PictureEdit pictureEdit=new PictureEdit();
        pictureEdit=pictureEditService.loadPicture(userId);
        if (pictureEdit != null) {
            return pictureEdit;
        } else {
            return null;
        }
    }
    @PostMapping("/getPictureList")
    public List<PictureEdit> getPictureList(@RequestParam("userId") String userId) {
        List<PictureEdit> pictureEditList= pictureEditService.getPictureList(userId);
        if (pictureEditList != null) {
            System.out.println(pictureEditList+"-----------------------------");
            return pictureEditList;
        } else {
            return null;
        }
    }

    @PostMapping("/uploadPicture")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("userId") String userId
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        try {
            // 检查目录是否存在，不存在则创建目录
            if (!Files.exists(Paths.get(uploadDirectorys))) {
                Files.createDirectories(Paths.get(uploadDirectorys));
            }
            // 生成随机的UUID
            UUID randomUUID = UUID.randomUUID();
            String fileName = file.getOriginalFilename();
            String filePath =  uploadDirectorys + fileName;
            String relativePath="pictureEdit/"+fileName;
            PictureEdit pictureEdit=new PictureEdit();
            pictureEdit.setId(randomUUID.toString());
            pictureEdit.setUserId(userId);
            pictureEdit.setPictureUrl(relativePath);
            file.transferTo(new File(filePath));
            pictureEditService.addPicture(pictureEdit);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

    @PostMapping("/delete")
    @Transactional
    public void deletePicture(String pictureUrl) {
        try {
            // 使用URI解析图片URL
            URI uri = new URI(pictureUrl);
            // 获取路径部分
            String path = uri.getPath();

            // 直接获取最后一个'/'之后的字符串，即图片名称
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            System.out.println(fileName);

            // 调用服务层进行删除操作，传入获取到的文件名
            pictureEditService.deletePicture("pictureEdit/"+fileName);
        } catch (URISyntaxException e) {
            // 处理URI解析异常
            throw new RuntimeException("Invalid picture URL format", e);
        }
    }


}
