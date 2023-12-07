    package com.example.android.controller;
    import com.example.android.entity.PostItem;
    import com.example.android.entity.PostWithUserInfo;
    import com.example.android.service.PostService;
    import com.example.android.service.UserInfoService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.util.FileCopyUtils;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    import java.io.*;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.*;

    @RestController
    @RequestMapping("/posts")
    public class PostController {
        @Autowired
        private PostService postService;
        @Autowired
        private UserInfoService userInfoService;
        private String uploadDirectory = "D:\\Upload\\post\\";
        @PostMapping("/upload")
        //帖子上传
        public void handleFileUpload(
                @RequestPart("post") PostItem post,
                @RequestParam("images") List<MultipartFile> files,
                @RequestParam("identifiers") List<String> identifiers,
                @RequestParam("sequenceNumbers") List<Integer> sequenceNumbers,
                @RequestParam("totalChunks") List<Integer> totalChunks
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
                        String fileName = identifier + "_" + sequenceNumber + "_" + file.getOriginalFilename();
                        // 保存文件分片
                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(uploadDirectory + fileName);
                        Files.write(path, bytes);
                        // 检查是否所有分片都已上传
                        if (sequenceNumber == totalChunk - 1) {
                            // 如果所有分片都已上传，则重组文件
                            String combinedFileName = identifier + "_" + file.getOriginalFilename();
                            fileNames.add("post/"+combinedFileName);
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
                post.setPicturePath(fileNames);
            }

            postService.createPost(post);
        }
        //帖子获取
        @GetMapping("/getpostlist")
        public List<PostWithUserInfo> getPostList(){
            List<PostWithUserInfo> postWithUserInfos = new ArrayList<>();
            List<PostItem> posts = postService.findPostList();
            for (PostItem post:posts){
                postWithUserInfos.add(new PostWithUserInfo(post,userInfoService.findByUserId(post.getUserId())));
            }
            return postWithUserInfos;
        }
    }
