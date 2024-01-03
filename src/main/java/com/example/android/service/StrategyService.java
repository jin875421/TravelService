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

import static java.lang.Math.toRadians;

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
        //获取数据库中Strategy集
        List<Strategy> strategyList = strategyRepository.findAll();
        //遍历数组进行距离比对
        for(int i=0;i<strategyList.size();i++){
            Strategy strategy1 = strategyList.get(i);
            if(strategyIsNear(strategy1,strategy)){
                strategy.setLatitude(strategy1.getLatitude());
                strategy.setLongitude(strategy1.getLongitude());
                break;
            }
        }
        strategyRepository.save(strategy);
    }
    //计算两点之间距离的公式
    private boolean strategyIsNear(Strategy strategy1, Strategy strategy2){
        double lat1, lon1;//本来存在的点
        double lat2, lon2;//新进的点
        //赋值
        lat1 = Double.parseDouble(strategy1.getLatitude());
        lon1 = Double.parseDouble(strategy1.getLongitude());
        lat2 = Double.parseDouble(strategy2.getLatitude());
        lon2 = Double.parseDouble(strategy2.getLongitude());
        //计算
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        if(c*6378.137 < 0.6){
            return true;
        }else {
            return false;
        }
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
    private boolean markerIsNear(Marker marker1, Marker marker2){
        double lat1, lon1;//本来存在的点
        double lat2, lon2;//新进的点
        //赋值
        lat1 = marker1.getLatitude();
        lon1 = marker1.getLongitude();
        lat2 = marker2.getLatitude();
        lon2 = marker2.getLongitude();
        //计算
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        System.out.println("计算得出两点之间的距离"+c*6378.137);
        if(c*6378.137 < 0.6){
            return true;
        }else {
            return false;
        }
    }

    public void addMarker(Marker marker){
        int flag = 1;
        System.out.println("开始保存marker");
        Marker marker1 = markerRepository.findByLatitudeAndLongitude(marker.getLatitude(), marker.getLongitude());
        if(marker1 != null) {
            System.out.println("marker还未存在");
            flag = 0;
        }
        //循环遍历寻找接近的点
        List<Marker> markerList = markerRepository.findAll();
        for (int i=0; i<markerList.size(); i++) {
            if(markerIsNear(markerList.get(i),marker)){
                System.out.println("找到接近的点");
                flag = 0;
                break;
            }
        }
        if(flag == 1){
            markerRepository.save(marker);
        }
    }

}
