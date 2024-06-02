package com.example.android.service;

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
}
