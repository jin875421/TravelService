package com.example.android.service;


import com.example.android.entity.*;
import com.example.android.repository.TravelPictureRepository;
import com.example.android.repository.TravelPlaceRepository;
import com.example.android.repository.TravelRepository;
import com.example.android.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TravelsService {
    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private TravelPlaceRepository travelPlaceRepository;
    @Autowired
    private TravelPictureRepository travelPictureRepository;




    //在这里写一个方法根据user_id查询来返回ShowPicture类型的list
    public List<ShowPicture> getPicturesShowedByUserId(String userId){
        List<ShowPicture> list = new ArrayList<>();
        //首先找到userId对应的所有travelId
        List<Travel> travels = travelRepository.findTravelsByUserId(userId);
        // jdk的lamda获取实体类的一个字段
        List<String> travelIds= travels.stream().map(p -> p.getTravelId()).collect(Collectors.toList());

        //现在找到了所有的TravelId
        //现在需要找到所有的placeId和travelDate，然后再根据placeId找到所有的picturePath，然后需要将所有的照片路径和日期对应上，
        //然后将对应上的信息封装成ShowPicture类型的对象，然后将所有这样的对象封装成list
        List<TravelPlace> travelPlaces = travelPlaceRepository.findTravelPlaceByTravelIdIn(travelIds);
        //然后再使用lambda来获取实体类的其中一个字段组成的list
//        List<Date> travelDates= travelPlaces.stream().map(p -> p.getTravelDate()).collect(Collectors.toList());

        //新建一个list，存放所有placeId？然后根据所有的placeId来确定顺序，将
        //貌似不用，现在只需要遍历，然后根据所有的遍历到的date放到新的实体类对象中，然后再根据遍历到的id来查找到所有的照片，
        //将照片封装成list，然后将list放到新建的实体类对象中，现在开始吧！
        for(TravelPlace travelPlace : travelPlaces) {

            ShowPicture sp = new ShowPicture();

            Date date = travelPlace.getTravelDate();
            //然后这里再根据id找到所有的照片
            List<TravelPicture> travelPictures = travelPictureRepository.findTravelPicturesByPlaceId(travelPlace.getPlaceId());
            List<String> picturePachs= travelPictures.stream().map(p -> p.getPicturePath()).collect(Collectors.toList());
            //然后将这两样数据封装到实体类的对象中
            sp.setTravelDate(date);
            sp.setPicturePath(picturePachs);
            //然后再将这个实体类对象sp放到list中
            list.add(sp);
        }
        //最后返回list
        return list;
        //TODO 目前逻辑上看来没有什么问题，但是需要在返回和接收数据的时候将数据封装到vo类里
    }

    public int createTravelRecoed(TravelRecord travelRecord) {
        //TODO 在这之前要检查userId是否存在，以及进行其他操作，
        //TODO 但是在检查是否有这个userId之前需要建立userInfo表中的数据
        //在这里需要将所有的数据分散到各个表中
        //首先是Travel类的对象
        Travel travel = new Travel();
        TravelPlace travelPlace = new TravelPlace();
        TravelPicture travelPicture = new TravelPicture();
        //使用UUID来分配travel_id
        //现在Id由前端分配
        //---------------------------------------------------------------------------------
//        String travelId = UUID.randomUUID().toString();
//        先来检查数据库中有没有对应旅程的id
        //根据id来查询条例，看有没有相关数据
        if(!travelRepository.findById(travelRecord.getTravelId()).isPresent()){
            travel.setTravelId(travelRecord.getTravelId());
            travel.setUserId(travelRecord.getUserId());
            travel.setTravelName(travelRecord.getTravelName());
            //将该数据添加到数据库中
            //在添加数据库的时候添加不进去！！！！！！！！！！！！！！！！！！！！！！！！！ ！！！！！！！！！！！！！！！
            travelRepository.save(travel);
        }

        //----------------------------------------------------------------------------------
        //使用UUID来分配placeId,并且分配当前的date
//        String placeId = UUID.randomUUID().toString();
//        LocalDate currentDate = LocalDate.now();
        //在这里，placeId应该不会重复存储到数据库
        travelPlace.setTravelId(travelRecord.getTravelId());
        travelPlace.setPlaceId(travelRecord.getPlaceId());
        travelPlace.setPlaceName(travelRecord.getPlaceName());
        travelPlace.setTravelExperience(travelRecord.getContent());
        travelPlace.setTravelDate(travelRecord.getDate());
        //将该数据添加到数据库中
        travelPlaceRepository.save(travelPlace);
        //----------------------------------------------------------------------------------
        //最后是照片数据
        //遍历！！！！！！！！！！！！！！！！！！！！
        for(String image : travelRecord.getImage()){
            //通过UUID分配id
            String pictureId = UUID.randomUUID().toString();
            travelPicture.setPictureId(pictureId);
            travelPicture.setPlaceId(travelRecord.getPlaceId());
            travelPicture.setPicturePath(image);
            //然后将该数据添加到数据库中
            travelPictureRepository.save(travelPicture);
            //TODO 这里出现了一个小问题，但是这是一个如果逻辑上正确就不会发生的问题，前端向后端发送的placeId和travelId都是前端自己生成的
            //TODO 最开始测试的时候发现，数据库中不同的pictureId对应着相同的picturePath和placeId
        }
        return 1;

    }

    public List<ShowTravel> listTravels(String userId) {
        List<ShowTravel> showTravelsList = new ArrayList<>();
        //首先根据userId查找到所有的travel
        List<Travel> travels = travelRepository.findTravelsByUserId(userId);
        for (Travel travel : travels){
            ShowTravel showTravel = new ShowTravel();
            List<String> images = new ArrayList<>();
            //通过travelId获取placeId列表
            List<TravelPlace> travelPlaces = travelPlaceRepository.findTravelPlacesByTravelId(travel.getTravelId());
            //遍历所有的placeId，并添加第一张照片至images中
            for(TravelPlace travelPlace : travelPlaces){
                images.add(travelPictureRepository.findTravelPicturesByPlaceId(travelPlace.getPlaceId()).get(0).getPicturePath());
            }
            showTravel.setUserId(travel.getUserId());
            showTravel.setTravelId(travel.getTravelId());
            showTravel.setTravelName(travel.getTravelName());
            showTravel.setImages(images);
            showTravelsList.add(showTravel);
        }
        return showTravelsList;
    }
    //这个方法用于实现将数据库中相应的旅行的日期数据和图片数据都取出来，是多表查询操作
    public List<ShowTravel> showTravels(String userId) {

        List<ShowTravel> showTravelsList = new ArrayList<>();

        //首先根据userId查找到所有的travel
        List<Travel> travels = travelRepository.findTravelsByUserId(userId);
        //然后根据所有的travel找到所有的travelId
        List<String> travelIds = travels.stream().map(p -> p.getTravelId()).collect(Collectors.toList());
        //然后再根据travelIds找到所有的日期和照片，通过遍历来实现
        for(String travelId :travelIds){
            //首先将所有的照片封装起来
            List<String> images = new ArrayList<>();
            //然后设置一个中间变量来放入最小日期
            Date mimDate = new Date();
            //这个实体类对象用于存放这个遍历中得到的所有信息
            ShowTravel showTravel = new ShowTravel();

            //根据这个id找到所有的placeId和所有的对应的照片和日期
            //首先找到所有的travelPlace
            List<TravelPlace> travelPlaces = travelPlaceRepository.findTravelPlacesByTravelId(travelId);
            //然后再根据这个list找到所有placeDate
            List<Date> travelDates = travelPlaces.stream().map(p -> p.getTravelDate()).collect(Collectors.toList());
            mimDate = travelDates.get(0);//这里注意，第一个应该是0吧。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
            //现在遍历这个list，找到最小的日期
            for (Date date : travelDates){
                if(mimDate.after(date)){
                    mimDate = date;
                }
            }
            //现在经过遍历，密码Date就是所有日期中最小的了
            showTravel.setDate(mimDate);

            //下一步，得到照片
            //首先得到所有的placeId,然后再根据所有的placeId得到照片
            List<String> placeIds = travelPlaces.stream().map(p -> p.getPlaceId()).collect(Collectors.toList());

            //遍历每一个id
            for(String placeId : placeIds){
                List<TravelPicture> pictures = travelPictureRepository.findTravelPicturesByPlaceId(placeId);
                List<String> picturePaths = pictures.stream().map(p -> p.getPicturePath()).collect(Collectors.toList());
                //这样就找到了所有的照片，现在将这个列表拼接到整体的列表中
                images.addAll(picturePaths);
            }
            //最后将所有的东西都封装到实体类中
            showTravel.setImages(images);
            showTravel.setTravelId(travelId);
            showTravel.setUserId(userId);
            //根据travelId找到travelName
            String trName = travelRepository.findTravelNameByTravelId(travelId);
            showTravel.setTravelName(trName);

            //然后将这个类封装到整体要返回的list中
            showTravelsList.add(showTravel);
        }
        return showTravelsList;
    }

    //这个方法用于实现点击一个条例进入到该次旅游的详情页面
    public List<TravelRecord> showATravel(String travelId) {
        //完犊子，还挺麻烦，要整的东西还挺多

        //首先，通过travel表中得到userId,然后将这个userId放到所有的list当中，并且将所有的list中放入这个travelId，travelName也是
        //总之，要使用这个已有的类，就需要将userId，travelId，travelName这三个共同的元素都放到类中
        //然后，开始遍历所有的place，将所有的place的信息封装到类中

        //开始吧
        //这里先定义一个list，用于封装要返回的数据
        List<TravelRecord> list = new ArrayList<>();
        //先找到travelName和userId
        Travel travel = travelRepository.findTravelsByTravelId(travelId);
        String userId = travel.getUserId();
        String travelName = travel.getTravelName();
        //然后获取到所有的place并遍历
        List<TravelPlace> travelPlaces = travelPlaceRepository.findTravelPlacesByTravelId(travelId);
        for(TravelPlace travelPlace : travelPlaces){
            //用一个实体类对象接收这些数据
            TravelRecord travelRecord = new TravelRecord();

            //在这里找到对应的 placeId,placeName,content,List<Image>
            String placeId = travelPlace.getPlaceId();
            String placeName = travelPlace.getPlaceName();
            String content = travelPlace.getTravelExperience();

            //然后将这些数据全都装实体类对象中
            travelRecord.setUserId(userId);
            travelRecord.setTravelId(travelId);
            travelRecord.setTravelName(travelName);
            travelRecord.setPlaceId(placeId);
            travelRecord.setPlaceName(placeName);
            travelRecord.setContent(content);

            //最后是添加照片列表
            //首先根据所有的placeId找到所有的对应的照片列表。。。。好像挺简单的
            List<TravelPicture> travelPictures = travelPictureRepository.findTravelPicturesByPlaceId(travelPlace.getPlaceId());
            List<String> image = travelPictures.stream().map(p -> p.getPicturePath()).collect(Collectors.toList());
            travelRecord.setImage(image);
            list.add(travelRecord);
        }

        return list;
    }
}









