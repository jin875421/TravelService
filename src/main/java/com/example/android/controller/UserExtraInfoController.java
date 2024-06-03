package com.example.android.controller;

import com.example.android.entity.UserExtraInfo;
import com.example.android.service.UserExtraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userExtraInfo")
public class UserExtraInfoController {
    @Autowired
    private UserExtraInfoService userExtraInfoService;

    @PostMapping("/getUserExtraInfo")
    public UserExtraInfo getUserExtraInfo(String userId) {
        UserExtraInfo userExtraInfo = userExtraInfoService.getUserExtraInfo(userId);
        return userExtraInfo;
    }

    @PostMapping("/update")
    public String update(UserExtraInfo userExtraInfo) {
        UserExtraInfo newUserExtraInfo = userExtraInfoService.updateUserExtraInfo(userExtraInfo);
        if (newUserExtraInfo == null) {
            return "fail";
        }
        return "success";
    }

}
