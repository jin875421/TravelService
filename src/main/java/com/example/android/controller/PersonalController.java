package com.example.android.controller;

import com.example.android.entity.Personal;
import com.example.android.entity.UserInfo;
import com.example.android.service.FileUploadService;
import com.example.android.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    private PersonalService personalService;
    @Autowired
    private FileUploadService fileUploadService;
    private String uploadDirectory = "D:\\Upload\\Personal\\";
    @GetMapping("/getBackground")
    public Personal getBackground(@RequestParam("userId") String userId) {
        Personal personal=new Personal();
        personal=personalService.loadBackground(userId);
        if (personal != null) {
            return personal;
        } else {
            return null;
        }
    }

    @PostMapping("/uploadbackground")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("userId") String userId
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        try {
            // 检查目录是否存在，不存在则创建目录
            if (!Files.exists(Paths.get(uploadDirectory))) {
                Files.createDirectories(Paths.get(uploadDirectory));
            }
            String fileName = file.getOriginalFilename();
            String filePath =  uploadDirectory + fileName;
            file.transferTo(new File(filePath));
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}
