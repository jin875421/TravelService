package com.example.android.controller;

import com.example.android.entity.Achievement;
import com.example.android.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/achievement")
public class AchievementController {
    @Autowired
    private AchievementService achievementService;
    @RequestMapping("/getAchievement")
    public List<Achievement> getAchievements(String userId)
    {
        return achievementService.getAchievement(userId);
    }
}
