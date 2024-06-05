package com.example.android.service;

import com.example.android.entity.Follow;
import com.example.android.entity.UserInfo;
import com.example.android.repository.FollowRepository;
import com.example.android.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FollowService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private FollowRepository followRepository;

    /**
     * 获取指定用户的关注列表。
     *
     * @param userId 需要获取关注列表的用户ID。
     * @return 返回该用户的关注列表，包含有效的关注记录。
     */
    public List<UserInfo> getFollowUserInfoList(String userId) {
        // 通过用户ID查询关注列表
        List<Follow> followList = followRepository.findByUserId(userId);

        // 遍历关注列表，验证关注的用户是否存在，若不存在则删除该关注记录
        followList.forEach(follow -> {
            UserInfo userInfo = userInfoRepository.findByUserId(follow.getFollowId());
            if (userInfo == null){
                followRepository.deleteByFollowId(follow.getFollowId());
            }
        });

        // 重新获取经过验证后的关注列表
        followList = followRepository.findByUserId(userId);

        // 遍历关注列表，获取关注用户的信息
        List<UserInfo> userInfoList = new ArrayList<>();
        followList.forEach(follow -> {
            UserInfo userInfo = userInfoRepository.findByUserId(follow.getFollowId());
            if (userInfo != null){
                userInfoList.add(userInfo);
            }
        });
        // 输出
        System.out.println(userInfoList);
        return userInfoList;
    }

    /**
     * 关注
     * @param userId
     * @param followId
     * @return 0: 成功 1:用户不存在 2:已关注
     */
    public int saveFollow(String userId, String followId,String groupOf) {
        //先检测关注的对象是否存在
        UserInfo userInfo = userInfoRepository.findByUserId(followId);
        if (userInfo == null){
            throw new RuntimeException("关注对象不存在");
        }
        //先检测是否已经关注过
        Follow follow = followRepository.findByUserIdAndFollowId(userId,followId);
        if(follow != null){
            return 2;
        }
        //未关注
        //动态生成UUID
        String id= UUID.randomUUID().toString();
        followRepository.save(new Follow(id,userId,followId,groupOf));
        return 0;
    }

    /**
     * 删除用户的关注记录
     * @param userId 被关注者的用户ID
     * @param followId 关注者的用户ID
     * 该方法首先根据给定的userId和followId查找对应的关注记录，如果找到，则删除该记录。
     */
    public String deleteFollow(String userId,String followId) {
        // 根据userId和followId查找关注记录
        Follow follow =followRepository.findByUserIdAndFollowId(userId,followId);
        if(follow != null){
            // 如果找到关注记录，则根据id删除该记录
            followRepository.deleteByUserIdAndFollowId(userId,followId);
            return "success";
        }
        return "fail";
    }

    /**
     * 批量删除用户的关注记录
     * @param userId 用户ID
     * @param unfollowIdList 需要删除的关注者ID列表
     */
    public String deleteFollows(String userId,List<String> unfollowIdList) {
        if(unfollowIdList == null || unfollowIdList.size() == 0){
            return "fail";
        }
        List<String> deleteList = new ArrayList<>();
        for(String followId : unfollowIdList){
            // 根据userId和followId查找关注记录
            Follow follow =followRepository.findByUserIdAndFollowId(userId,followId);
            if(follow != null){
                // 如果找到关注记录,记录下followId
                deleteList.add(follow.getFollowId());
            } else {
                // 存在未知记录 需要排查问题 不执行删除操作
                return "Unknown records exist";
            }
        }
        deleteList.forEach(followId -> {
            followRepository.deleteByUserIdAndFollowId(userId,followId);
        });
        return "success";
    }

    /**
     * 检查某个用户是否已经关注了另一个用户。
     *
     * @param userId 用户ID，表示需要检查是否关注的用户的ID。
     * @param followId 被关注用户的ID。
     * @return 返回一个布尔值，如果指定用户已经关注了被关注用户，则返回true；否则返回false。
     */
    public Boolean followExist(String userId, String followId) {
        // 通过用户ID和被关注用户ID查询关注关系
        Follow follow =followRepository.findByUserIdAndFollowId(userId,followId);
        // 如果查询结果不为空，表示关注关系存在，返回true
        if(follow != null) return true;
        // 查询结果为空，表示关注关系不存在，返回false
        return false;
    }

    public List<Follow> getFollowList(String userId) {
        return followRepository.findByUserId(userId);
    }

    public int getFansCount(String userId) {
        return followRepository.findAllByFollowId(userId).size();
    }
    public int getFollowCount(String userId){
        return followRepository.findByUserId(userId).size();
    }
}
