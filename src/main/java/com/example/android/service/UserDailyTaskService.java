package com.example.android.service;

import com.example.android.entity.UserDailyTask;
import com.example.android.entity.UserExtraInfo;
import com.example.android.entity.UserInfo;
import com.example.android.repository.UserDailyTaskRepository;
import com.example.android.repository.UserExtraInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserDailyTaskService {

    @Autowired
    private UserDailyTaskRepository userDailyTaskRepository;
    @Autowired
    private UserExtraInfoRepository userExtraInfoRepository;

    public List<UserDailyTask> getDailyTasksByUserId(String userId) {
        List<UserDailyTask> userDailyTasks = userDailyTaskRepository.findByUserId(userId);
        // 照顾老用户
        if (userDailyTasks.isEmpty()) {
            return creatUserDailyTasks(userId);
        }
        // 获取当前时间戳
        Timestamp now = new Timestamp(System.currentTimeMillis());
        // 如果任务中的时间戳小于当前时间戳(只判断日期)，进度重置在发送给用户，同步更新数据库
        LocalDate localDate;
        LocalDate nowDate = now.toLocalDateTime().toLocalDate();
        for (UserDailyTask task : userDailyTasks) {
            localDate = Instant.ofEpochMilli(task.getLastUpdated().getTime())
                .atZone(ZoneId.of("Asia/Shanghai")) // 指定时区为上海，代表北京时区
                .toLocalDate();
            if (localDate.isBefore(nowDate)&&!task.getTaskName().equals("每日登录打卡")) {
                task.setProgress(0);
                task.setCompleted(false);
                task.setLastUpdated(now);
                userDailyTaskRepository.save(task);
            }else if (task.getTaskName().equals("每日登录打卡")&&localDate.isBefore(nowDate)){
                task.setCompleted(false);
                userDailyTaskRepository.save(task);
            }
        }
        return userDailyTasks;
    }

    public void updateTaskProgress(String taskId, int progress) {
        UserDailyTask task = userDailyTaskRepository.findById(taskId).orElse(null);
        //先执行判断是否是本日第一次执行
        if(task!=null){
            // 获取当前时间戳
            Timestamp now = new Timestamp(System.currentTimeMillis());
            // 如果任务中的时间戳小于当前时间戳(只判断日期)，进度重置在发送给用户，同步更新数据库
            LocalDate localDate;
            LocalDate nowDate = now.toLocalDateTime().toLocalDate();
            localDate = Instant.ofEpochMilli(task.getLastUpdated().getTime())
                    .atZone(ZoneId.of("Asia/Shanghai")) // 指定时区为上海，代表北京时区
                    .toLocalDate();
            if (localDate.isBefore(nowDate)) {
                task.setProgress(0);
                task.setCompleted(false);
                task.setLastUpdated(now);
                userDailyTaskRepository.save(task);
            }
        }
        if (task != null) {
            task.setProgress(progress);
            task.setCompleted(progress >= task.getMaxProgress());
            userDailyTaskRepository.save(task);
        }
    }

    public List<UserDailyTask> creatUserDailyTasks(String userId) {
        List<UserDailyTask> newTasks = new ArrayList<>();
        newTasks.add(new UserDailyTask(
                UUID.randomUUID().toString(),
                userId,
                "每日登录打卡",
                0,
                1,
                30,
                false,
                new Timestamp(System.currentTimeMillis()),
                UserDailyTask.DAILY_TASK_TYPE_DAILY,
                "userDailyTaskIcon/clock.png"));
        newTasks.add(new UserDailyTask(
                UUID.randomUUID().toString(),
                userId,
                "浏览3个帖子",
                0,
                3,
                20,
                false,
                new Timestamp(System.currentTimeMillis()),
                UserDailyTask.DAILY_TASK_TYPE_DAILY,
                "userDailyTaskIcon/browse.png"));
        newTasks.add(new UserDailyTask(
                UUID.randomUUID().toString(),
                userId,
                "完成5次点赞",
                0,
                5,
                30,
                false,
                new Timestamp(System.currentTimeMillis()),
                UserDailyTask.DAILY_TASK_TYPE_DAILY,
                "userDailyTaskIcon/like.png"));
        return userDailyTaskRepository.saveAll(newTasks);
    }

    public String upupdateByUserIdAndTag(String userId, String tag) {
        UserDailyTask task = userDailyTaskRepository.findByUserIdAndTaskName(userId, tag);
        checkTask(task);
        if(tag.equals("每日登录打卡")){
            return addExp(task);
        }
        if(tag.equals("浏览3个帖子")){
            return addExp(task);
        }
        if(tag.equals("完成5次点赞")){
            return addExp(task);
        }
        return "fail";
    }
    private String addExp(UserDailyTask task){
        if(task.getCompleted()){
            return "success";
        } else {
            // 更新任务进度
            task.setProgress(task.getProgress() + 1);
            task.setCompleted(task.getProgress() >= task.getMaxProgress());
            userDailyTaskRepository.save(task);

            // 更新用户经验和等级
            UserExtraInfo userExtraInfo = userExtraInfoRepository.findByUserId(task.getUserId());
            int currentExp = userExtraInfo.getExperience();
            int reward = task.getReward();
            int currentLevel = userExtraInfo.getLevel();
            // 升级计算 0->1 :(lv+1)*100 + lv*50=100   1->2 :250   2->3 :400   3->4 :600   4->5 :750
            if(currentExp + reward >= (currentLevel+1)*100 + currentLevel*50 && currentLevel < 5){
                userExtraInfo.setExperience(currentExp + reward - (currentLevel+1)*100 - currentLevel*50);
                userExtraInfo.setLevel(currentLevel + 1);
            } else {
                userExtraInfo.setExperience(currentExp + reward);
            }
            userExtraInfoRepository.save(userExtraInfo);
            if (task.getTaskName().equals("每日登录打卡")){
                return String.valueOf(task.getProgress() );
            }
            return "success";
        }
    }
    private void checkTask(UserDailyTask task){
        //先执行判断是否是本日第一次执行
        if(task!=null){
            // 获取当前时间戳
            Timestamp now = new Timestamp(System.currentTimeMillis());
            // 如果任务中的时间戳小于当前时间戳(只判断日期)，进度重置在发送给用户，同步更新数据库
            LocalDate localDate;
            LocalDate nowDate = now.toLocalDateTime().toLocalDate();
            localDate = Instant.ofEpochMilli(task.getLastUpdated().getTime())
                    .atZone(ZoneId.of("Asia/Shanghai")) // 指定时区为上海，代表北京时区
                    .toLocalDate();
            System.out.println(task.getLastUpdated());
            if (localDate.isBefore(nowDate)&&!task.getTaskName().equals("每日登录打卡")) {
                task.setProgress(0);
                task.setCompleted(false);
                task.setLastUpdated(now);
                userDailyTaskRepository.save(task);

            }else if (task.getTaskName().equals("每日登录打卡")){
                //执行每日打卡判断,查看上次打卡时间是否为昨天
                System.out.println(localDate);
//                System.out.println(nowDate.minusDays(1));
//                System.out.println(nowDate);
                if(localDate.isBefore(nowDate.minusDays(1))){
                    System.out.println("11111");
                    task.setProgress(0);
                    task.setCompleted(false);
                    task.setLastUpdated(now);
                    userDailyTaskRepository.save(task);

                }else if (localDate.isBefore(nowDate)){
                    System.out.println("3333");
                    task.setCompleted(false);
                    task.setLastUpdated(now);
                    userDailyTaskRepository.save(task);

                }

            }

        }

    }
}
