package com.example.androdid.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class FileUploadService {
    @Value("${upload.path}\\avatar")
    private String uploadPath ;  // 你的上传路径

    public String storeFile(MultipartFile file) {
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();

            // 构建文件路径
            Path directoryPath = Paths.get(uploadPath);
            Path filePath = directoryPath.resolve(fileName);

            // 如果目录不存在，创建目录
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }


            // 将上传文件写入目标文件
            Files.write(filePath, file.getBytes());
            return "avatar/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(e);
        }
    }
}
