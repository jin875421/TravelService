package com.example.android.controller;

import com.example.android.entity.UserExtraInfo;
import com.example.android.service.UserExtraInfoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userExtraInfo")
public class UserExtraInfoController {
    Gson gson = new Gson();
    @Autowired
    private UserExtraInfoService userExtraInfoService;

    @PostMapping("/getUserExtraInfo")
    public UserExtraInfo getUserExtraInfo(@RequestParam String userId) {
        UserExtraInfo userExtraInfo = userExtraInfoService.getUserExtraInfo(userId);
        return userExtraInfo;
    }

    @PostMapping("/update")
    public String update(@RequestParam String updateUserExtraInfoStr) {
        UserExtraInfo updateUserExtraInfo = gson.fromJson(updateUserExtraInfoStr, UserExtraInfo.class);
        int result = userExtraInfoService.updateUserExtraInfo(updateUserExtraInfo);
        if (result > 0) {
            return "success";
        }
        return "fail";
    }

}
