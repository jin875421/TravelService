package com.example.android.controller;

import com.example.android.entity.Follow;
import com.example.android.entity.UserInfo;
import com.example.android.service.FollowService;
import com.example.android.service.UserExtraInfoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private UserExtraInfoService userExtraInfoService;
    @GetMapping("/getGroupInfo")
    public String getGroupInfo(@RequestParam String userId){
        String followGroupInfo = userExtraInfoService.getGroupInfo(userId);
        return followGroupInfo;
    }
    @GetMapping("isFollow")
    public String isFollow(@RequestParam String userId,@RequestParam String followId){
        Boolean isFollow = followService.followExist(userId,followId);
        if(isFollow) return "true";
        return "false";
    }
    @GetMapping("/getFollowUserInfoList")
    public List<UserInfo> getFollowUserInfoList(@RequestParam String userId){
        return followService.getFollowUserInfoList(userId);
    }
    @GetMapping("/getFollowList")
    public List<Follow> getFollowList(@RequestParam String userId){
        return followService.getFollowList(userId);
    }
    @PostMapping("/deleteFollow")
    public String deleteFollow(@RequestParam String userId,@RequestParam String followId){
        followService.deleteFollow(userId,followId);
        return "success";
    }

    @PostMapping("/addFollow")
    public String addFollow(@RequestParam String userId,@RequestParam String followId,@RequestParam(defaultValue = "[\"全部\"]")String groupOf){
        followService.saveFollow(userId,followId,groupOf);
        return "success";
    }
    @PostMapping("/deleteFollows")
    public String deleteFollows(@RequestParam String userId,@RequestParam String unfollowIds){
        Gson gson = new Gson();
        List<String> unfollowIdList = gson.fromJson(unfollowIds,List.class);
        followService.deleteFollows(userId,unfollowIdList);
        return "success";
    }
    //获取粉丝数
    @GetMapping("/getFansCount")
    public int getFansCount(@RequestParam String userId){
        return followService.getFansCount(userId);
    }
    @GetMapping("/getFollowCount")
    public int getFollowCount(@RequestParam String userId){
        return followService.getFollowCount(userId);
    }

}
