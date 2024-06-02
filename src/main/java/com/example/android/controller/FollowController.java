package com.example.android.controller;

import com.example.android.entity.UserInfo;
import com.example.android.service.FollowService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;
    @GetMapping("isFollow")
    public String isFollow(@RequestParam String userId,@RequestParam String followId){
        Boolean isFollow = followService.followExist(userId,followId);
        if(isFollow) return "true";
        return "false";
    }
    @GetMapping("/getFollowList")
    public List<UserInfo> getFollowList(@RequestParam String userId){
        return followService.getFollowList(userId);
    }
    @PostMapping("/deleteFollow")
    public String deleteFollow(@RequestParam String userId,@RequestParam String followId){
        followService.deleteFollow(userId,followId);
        return "success";
    }

    @PostMapping("/addFollow")
    public String addFollow(@RequestParam String userId,@RequestParam String followId){
        followService.saveFollow(userId,followId);
        return "success";
    }
    @PostMapping("/deleteFollows")
    public String deleteFollows(@RequestParam String userId,@RequestParam String unfollowIds){
        Gson gson = new Gson();
        List<String> unfollowIdList = gson.fromJson(unfollowIds,List.class);
        followService.deleteFollows(userId,unfollowIdList);
        return "success";
    }
}
