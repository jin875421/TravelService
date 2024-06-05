package com.example.android.service;

import com.example.android.entity.Achievement;
import com.example.android.entity.UserExtraInfo;
import com.example.android.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AchievementService {
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private UserExtraInfoService userExtraInfoService;
    public List<Achievement> getAchievement(String userId) {
        UserExtraInfo userExtraInfo = userExtraInfoService.getUserExtraInfo(userId);
        //读取用户成就列表
        String achievementStr = userExtraInfo.getAchievement();
        List<String> achievement = new ArrayList<>();
        // 以逗号分隔
        if (!achievementStr.isEmpty()) {
            String[] items = achievementStr.split(",");
            for (String item : items) {
                achievement.add(item.trim()); // 添加并去除可能的前后空白
            }
        }
        List<Achievement> achievements = new ArrayList<>();
        for(String item:achievement){
            Achievement achievement1 = achievementRepository.findById(Integer.parseInt(item)).get();
            achievements.add(achievement1);
        }
        return achievements;
    }
}
