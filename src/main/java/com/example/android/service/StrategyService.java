package com.example.android.service;

import com.example.android.entity.*;
import com.example.android.repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class StrategyService {
    @Autowired
    StrategyRepository strategyRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    MarkerRepository markerRepository;
    @Autowired
    StrategyPictureRepository strategyPictureRepository;

    public void saveStrategyPicture(String strategyId, List<String> picturePath){
        for(String path:picturePath){
            strategyPictureRepository.save(new StrategyPicture(UUID.randomUUID().toString(),strategyId, path));
        }
    }

    public void saveMarker(Marker marker){
        if(markerRepository.findByLatitudeAndLongitude(marker.getLatitude(),marker.getLongitude())!=null){
            System.out.println("该点已经存在");
        }else {
            markerRepository.save(marker);
        }
    }

    public HashMap<String, String> getIconMap(){
        String id = "";
        String path = "";
        String key = "";
        List<Marker> markerList = markerRepository.findAll();
        HashMap<String, String> iconMap = new HashMap<>();
        for(Marker marker : markerList){
            id = strategyRepository.findAllByLatitudeAndLongitude(String.valueOf(marker.getLatitude()), String.valueOf(marker.getLongitude())).get(0).getStrategyId();
            path = strategyPictureRepository.findByStrategyId(id).get(0).getPicturePath();
            key = marker.getLatitude()+"+"+marker.getLongitude();
            iconMap.put(key, path);
        }
        return iconMap;
    }

    public ReturnStrategy getStrategyById(String strategyId){
        Strategy strategy = strategyRepository.findById(strategyId).get();
        return new ReturnStrategy(getUserNameByUserId(strategy.getUserId()),
                getAvatarByUserId(strategy.getUserId()),
                strategy.getUserId(),
                strategy.getStrategyId(),
                strategy.getTitle(),
                strategy.getDetail(),
                strategy.getTime(),
                strategy.getLatitude(),
                strategy.getLongitude(),
                strategyPictureRepository.findByStrategyId(strategy.getStrategyId()));
    }
    public List<ReturnStrategy> getStrategy(String latitude, String longitude){
        List<Strategy> strategyList = strategyRepository.findAllByLatitudeAndLongitude(latitude,longitude);
        List<ReturnStrategy> returnStrategyList = new ArrayList<>();
        for(Strategy strategy:strategyList){
            returnStrategyList.add(new ReturnStrategy(getUserNameByUserId(strategy.getUserId()),
                    getAvatarByUserId(strategy.getUserId()),
                    strategy.getUserId(),
                    strategy.getStrategyId(),
                    strategy.getTitle(),
                    strategy.getDetail(),
                    strategy.getTime(),
                    strategy.getLatitude(),
                    strategy.getLongitude(),
                    strategyPictureRepository.findByStrategyId(strategy.getStrategyId())));
        }

        return returnStrategyList;
    }

    public void addStrategy(Strategy strategy){
        strategyRepository.save(strategy);
    }

    public String getUserNameByUserId(String userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        System.out.println("通过Id:"+userInfo.getUserId()+"查询到的用户名"+userInfo.getUserName());
        String userName = userInfo.getUserName();
        return userName;
    }

    //查询头像
    public String getAvatarByUserId(String userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        System.out.println("通过Id:"+userInfo.getUserId()+"查询到的头像"+userInfo.getAvatar());
        String avatar = userInfo.getAvatar();
        return avatar;
    }

    public List<Marker> getMarker(){
        List<Marker> markerList = markerRepository.findAll();
        return markerList;
    }

    public void addMarker(Marker marker){
        System.out.println("开始保存marker");
        Marker marker1 = markerRepository.findByLatitudeAndLongitude(marker.getLatitude(), marker.getLongitude());
        if(marker1 == null) {
            System.out.println("marker还未存在");
            markerRepository.save(marker);
        }
    }

}
