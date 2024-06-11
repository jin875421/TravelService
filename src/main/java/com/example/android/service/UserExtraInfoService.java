package com.example.android.service;

import com.example.android.entity.UserExtraInfo;
import com.example.android.entity.UserInfo;
import com.example.android.repository.AchievementRepository;
import com.example.android.repository.PostRepository;
import com.example.android.repository.UserExtraInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserExtraInfoService {
    @Autowired
    private UserExtraInfoRepository userExtraInfoRepository;
    @Autowired
    private PostRepository postRepository;
    public String getGroupInfo(String userId) {
        return userExtraInfoRepository.findByUserId(userId).getFollowGroupInfo();
    }

    public UserExtraInfo getUserExtraInfo(String userId) {
        if (userExtraInfoRepository.findByUserId(userId) == null) {
            // 老用户登录 数据库中没有对应数据
            UserInfo userInfo = new UserInfo(userId);
            return addUserExtraInfo(userInfo);
        }
        return userExtraInfoRepository.findByUserId(userId);
    }

    /**
     * 添加新用户或为存有额外信息的用户添加额外信息。
     * <p>
     * 通过接收一个UserInfo对象，创建一个UserExtraInfo对象，并初始化其属性。
     *
     * @param userInfo 用户信息对象，包含用户的信息。
     * @return 返回保存后的UserExtraInfo对象，包含用户的额外信息和标识。
     */
    public UserExtraInfo addUserExtraInfo(UserInfo userInfo) {
        // 创建一个新的UserExtraInfo对象，初始化userId和tags
        UserExtraInfo newUserExtraInfo = new UserExtraInfo(userInfo.getUserId(), "[\"全部\"]");
        // 保存新的用户额外信息到数据库，并返回保存后的对象
        return userExtraInfoRepository.save(newUserExtraInfo);
    }

    /**
     * 更新用户额外信息的方法。
     * <p>
     * 通过调用用户额外信息仓库的保存方法，来更新用户额外信息。此方法接收一个UserExtraInfo对象作为参数，
     * 该对象包含了需要更新的用户额外信息。
     *
     * @param userExtraInfo 包含需要更新的用户额外信息的对象。
     * @return 更新后的UserExtraInfo对象。
     */
    public int updateUserExtraInfo(UserExtraInfo userExtraInfo) {
        userExtraInfoRepository.save(userExtraInfo);
        return 1;
    }

    public void addPostcount(String userId) {
        UserExtraInfo userExtraInfo = userExtraInfoRepository.findByUserId(userId);
        userExtraInfo.setPostCount(userExtraInfo.getPostCount() + 1);
        List<String> achievement = new ArrayList<>();
        if (userExtraInfo.getPostCount()+1 > 0){
            achievement.add("1");
        }
        if (userExtraInfo.getPostCount()+1 >= 10){
            achievement.add("2");
        }
        if (userExtraInfo.getPostCount()+1 >= 50){
            achievement.add("3");
        }
        if  (userExtraInfo.getLiked() > 0){
            achievement.add("4");
        }
        if (userExtraInfo.getLiked() >= 50){
            achievement.add("5");
        }
        if (userExtraInfo.getLiked() >= 200){
            achievement.add("6");
        }
        if (userExtraInfo.getCollected() > 0){
            achievement.add("7");
        }
        if (userExtraInfo.getCollected() >= 10){
            achievement.add("8");
        }
        if (userExtraInfo.getCollected() >= 50){
            achievement.add("9");
        }
        if (!achievement.isEmpty()){
            String achievementString = achievement.toString();
            //去除两端中括号
            achievementString = achievementString.substring(1, achievementString.length() - 1);
            userExtraInfo.setAchievement(achievementString);
            userExtraInfoRepository.updateUserExtraInfo(userExtraInfo);
        }


        }

    public void addCollectedCount(String postId) {
        //通过postId查询作者Id
        String userId = postRepository.findByPostId(postId).getUserId();
        UserExtraInfo userExtraInfo = userExtraInfoRepository.findByUserId(userId);
        userExtraInfo.setCollected(userExtraInfo.getCollected() + 1);
        List<String> achievement = new ArrayList<>();
        if (userExtraInfo.getPostCount() > 0){
            achievement.add("1");
        }
        if (userExtraInfo.getPostCount() >= 10){
            achievement.add("2");
        }
        if (userExtraInfo.getPostCount() >= 50){
            achievement.add("3");
        }
        if  (userExtraInfo.getLiked() > 0){
            achievement.add("4");
        }
        if (userExtraInfo.getLiked() >= 50){
            achievement.add("5");
        }
        if (userExtraInfo.getLiked() >= 200){
            achievement.add("6");
        }
        if (userExtraInfo.getCollected()+1 > 0){
            achievement.add("7");
        }
        if (userExtraInfo.getCollected()+1 >= 10){
            achievement.add("8");
        }
        if (userExtraInfo.getCollected()+1 >= 50){
            achievement.add("9");
        }
        if (!achievement.isEmpty()){
            String achievementString = achievement.toString();
            //去除两端中括号
            achievementString = achievementString.substring(1, achievementString.length() - 1);
            userExtraInfo.setAchievement(achievementString);
            userExtraInfoRepository.updateUserExtraInfo(userExtraInfo);
        }
    }

    public void addLikedCount(String postId) {
        //通过postId查询作者Id
        String userId = postRepository.findByPostId(postId).getUserId();
        UserExtraInfo userExtraInfo = userExtraInfoRepository.findByUserId(userId);
        userExtraInfo.setCollected(userExtraInfo.getLiked() + 1);
        List<String> achievement = new ArrayList<>();
        if (userExtraInfo.getPostCount() > 0){
            achievement.add("1");
        }
        if (userExtraInfo.getPostCount() >= 10){
            achievement.add("2");
        }
        if (userExtraInfo.getPostCount() >= 50){
            achievement.add("3");
        }
        if  (userExtraInfo.getLiked() +1> 0){
            achievement.add("4");
        }
        if (userExtraInfo.getLiked() +1>= 50){
            achievement.add("5");
        }
        if (userExtraInfo.getLiked() +1>= 200){
            achievement.add("6");
        }
        if (userExtraInfo.getCollected() > 0){
            achievement.add("7");
        }
        if (userExtraInfo.getCollected() >= 10){
            achievement.add("8");
        }
        if (userExtraInfo.getCollected() >= 50){
            achievement.add("9");
        }
        if (!achievement.isEmpty()){
            String achievementString = achievement.toString();
            //去除两端中括号
            achievementString = achievementString.substring(1, achievementString.length() - 1);
            userExtraInfo.setAchievement(achievementString);
            userExtraInfoRepository.updateUserExtraInfo(userExtraInfo);
        }
    }
}
