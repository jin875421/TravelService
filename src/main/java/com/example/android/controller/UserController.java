package com.example.android.controller;

import cn.hutool.crypto.digest.MD5;
import com.example.android.entity.UserInfo;
import com.example.android.service.FileUploadService;
import com.example.android.service.UserInfoService;
import com.example.android.utils.EmailUtil;
import com.example.android.utils.SmsUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private FileUploadService fileUploadService;
    SmsUtil smsUtil = new SmsUtil();

    private final Cache<String, String> codeCache = Caffeine.newBuilder()
            .expireAfterWrite(2, TimeUnit.MINUTES) // 设置验证码的过期时间，例如2分钟
            .build();

    @PostMapping("/upload")
    public ResponseEntity<String> updateUserData(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userName") String userName,
            @RequestParam("userId") String userId
    ) {
        if (file.isEmpty() && userName.isEmpty()) {
            return ResponseEntity.badRequest().body("数据为空请填写。");
        }

        UserInfo userInfo=userInfoService.findByUserId(userId);
        // 保存文件并获取相对路径
        if(!file.isEmpty()){
            String relativePath = fileUploadService.storeFile(file);
            userInfo.setAvatar(relativePath);
        }
        // 更新用户信息

        if (!userName.isEmpty()){
            userInfo.setUserName(userName);
        }
        UserInfo user=userInfoService.update(userInfo);
        if(userInfo==user){
            return ResponseEntity.ok("用户数据更新成功！");
        }else {
            return ResponseEntity.ok("用户数据更新失败！");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserInfo user) {
        user.setPassword(MD5.create().digestHex16(user.getPassword()));
        UserInfo userInfo = userInfoService.login(user.getUserId(), user.getPassword());
        if (userInfo != null) {
            String userId = userInfo.getUserId(); // 获取用户ID
            String userName=userInfo.getUserName();//获取昵称
            String responseJson = String.format("{'resultCode': 1, 'msg': '登录成功', 'userId': '%s','userName':'%s'}", userId,userName);
            return ResponseEntity.ok(responseJson);
        } else {
            // 登录失败
            return ResponseEntity.ok("{'resultCode': 0, 'msg': '用户名或密码错误'}");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody UserInfo user) {
        UserInfo userInfo = userInfoService.register(user.getUserId());
        if (userInfo == null) {
            // 注册成功
            user.setPassword(MD5.create().digestHex16(user.getPassword()));
            UserInfo add = userInfoService.addUserInfo(user);
            return ResponseEntity.ok("{'resultCode': 1, 'msg': '注册成功'}");
        } else {
            return ResponseEntity.ok("{'resultCode': 0, 'msg': '用户名已存在请更改'}");
        }

    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody UserInfo user) {
        UserInfo userInfo = null;
        String storedCode = null;
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userInfo = userInfoService.forgotPassword(user.getEmail());
            storedCode = codeCache.getIfPresent(user.getEmail());
        } else if (user.getUserPhoneNumber() != null && !user.getUserPhoneNumber().isEmpty()) {
            userInfo = userInfoService.phoneLogin(user.getUserPhoneNumber());
            storedCode = codeCache.getIfPresent(user.getUserPhoneNumber());
        }
        if (userInfo != null) {
            if (storedCode != null && storedCode.equals(user.getCode())) {
                // 更新密码
                user.setPassword(MD5.create().digestHex16(user.getPassword()));
                userInfo.setPassword(user.getPassword());
                UserInfo update = userInfoService.update(userInfo);
                if (update != null) {
                    // 更新成功
                    return ResponseEntity.ok("{'resultCode': 1, 'msg': '更新成功'}");
                } else {
                    // 更新失败
                    return ResponseEntity.ok("{'resultCode': 0, 'msg': '更新失败请重试'}");
                }
            } else {
                // 验证码不正确
                return ResponseEntity.ok("{'resultCode': 0, 'msg': '验证码不正确或已过期，请重试'}");
            }
        }else {
            return ResponseEntity.ok("{'resultCode': 0, 'msg': '用户不存在，请重试'}");
        }

    }
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody UserInfo user) {
        // 提取邮箱地址
        String toEmail = user.getEmail();

        // 调用 EmailUtil 中的 sendEmail 方法发送邮件
        String emailSent=EmailUtil.sendEmail(toEmail);
        codeCache.put(toEmail, emailSent);
        if (!emailSent.isEmpty()) {
            // 邮件发送成功，执行更新操作
            return ResponseEntity.ok("{'resultCode': 1, 'msg': '发送成功'}");
        } else {
            // 邮件发送失败
            return ResponseEntity.ok("{'resultCode': 0, 'msg': '发送失败请重试，请确认邮箱'}");
        }
    }
    @PostMapping("/sendSms")
    public ResponseEntity<String> sendSms(@RequestBody UserInfo userInfo) {
        String phoneNumber = userInfo.getUserPhoneNumber();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return ResponseEntity.ok("{'resultCode': 0, 'msg': '发送失败请重试，手机号为空'}");
        }

        // 调用发送短信的工具类
        String code = smsUtil.sendSms(phoneNumber);
        if (!code.isEmpty()) {
            // 短信发送成功，将验证码存入缓存
            codeCache.put(phoneNumber, code);
            return ResponseEntity.ok("{'resultCode': 1, 'msg': '发送成功'}");
        } else {
            // 短信发送失败
            return ResponseEntity.ok("{'resultCode': 0, 'msg': '发送失败请重试，请确认手机号'}");
        }
    }
    @PostMapping("/emailOrPhoneLogin")
    public ResponseEntity<String> emailOrPhoneLogin(@RequestBody UserInfo user) {
        UserInfo userInfo = null;
        String storedCode = null;
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userInfo = userInfoService.emailLogin(user.getEmail());
            storedCode = codeCache.getIfPresent(user.getEmail());
        } else if (user.getUserPhoneNumber() != null && !user.getUserPhoneNumber().isEmpty()) {
            userInfo = userInfoService.phoneLogin(user.getUserPhoneNumber());
            storedCode = codeCache.getIfPresent(user.getUserPhoneNumber());
        }

        if (userInfo != null) {
            if (storedCode != null && storedCode.equals(user.getCode())) {
                String userId = userInfo.getUserId();
                String userName = userInfo.getUserName();
                String responseJson = String.format("{'resultCode': 1, 'msg': '登录成功', 'userId': '%s','userName':'%s'}", userId, userName);
                return ResponseEntity.ok(responseJson);
            } else {
                return ResponseEntity.ok("{'resultCode': 0, 'msg': '验证码不正确或已过期，请重试'}");
            }
        } else {
            return ResponseEntity.ok("{'resultCode': 0, 'msg': '用户不存在，请重试'}");
        }
    }


    @PostMapping("/updateData")
    public ResponseEntity<String> updateData(@RequestBody UserInfo user) {
        UserInfo userInfo=userInfoService.findByUserId(user.getUserId());
        if (userInfo !=null) {
            userInfo.setUserName(user.getUserName());
            UserInfo updateData = userInfoService.update(userInfo);
            return ResponseEntity.ok("{'resultCode': 1, 'msg': '修改成功'}");
        } else {
            return ResponseEntity.ok("{'resultCode': 0, 'msg': '修改失败'}");
        }

    }
    @GetMapping("/getAvatar")
    public String getAvatar(@RequestParam("userId") String userId) {
        UserInfo userInfo = userInfoService.findByUserId(userId);
        if (userInfo != null) {
            String avatarUrl = userInfo.getAvatar();
            String userName=userInfo.getUserName();
            System.out.println("name"+userName);
            if (avatarUrl != null&&!avatarUrl.isEmpty()) {
                return avatarUrl+":"+userName;
            } else {
                // Handle the case when avatarUrl is null
                return "sadasd"+":"+userName;
            }
        } else {
            // Handle the case when userInfo is null
            return "";
        }
    }
    @GetMapping("/findName")
    public String findName(@RequestParam("userId")String userId) {
        UserInfo userInfo = userInfoService.findByUserId(userId);
        String userName = userInfo.getUserName();
        System.out.println("name"+userName);
        if (userInfo != null) {
            if (userName != null) {
                return userName;
            } else {
                // Handle the case when avatarUrl is null
                return "";
            }
        } else {
            return null;
        }
    }
}
