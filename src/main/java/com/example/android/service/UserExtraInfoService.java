package com.example.android.service;

import com.example.android.entity.UserExtraInfo;
import com.example.android.entity.UserInfo;
import com.example.android.repository.UserExtraInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserExtraInfoService {
    @Autowired
    private UserExtraInfoRepository userExtraInfoRepository;
    public String getGroupInfo(String userId) {
        return userExtraInfoRepository.findByUserId(userId).getFollowGroupInfo();
    }

    public UserExtraInfo getUserExtraInfo(String userId) {
        if(userExtraInfoRepository.findByUserId(userId)==null){
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
     * 该对象包含了需要更新的用户额外信息。方法返回更新后的UserExtraInfo对象，该对象是保存操作的结果。
     *
     * @param userExtraInfo 包含需要更新的用户额外信息的对象。
     * @return 更新后的UserExtraInfo对象。
     */
    public UserExtraInfo updateUserExtraInfo(UserExtraInfo userExtraInfo) {
        return userExtraInfoRepository.save(userExtraInfo);
    }
}
