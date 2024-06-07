package com.example.android.controller;

import com.example.android.entity.UserDailyTask;
import com.example.android.service.UserDailyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userDailyTask")
public class UserDailyTaskController {
    @Autowired
    private UserDailyTaskService userDailyTaskService;

    @GetMapping("/getTasks")
    public List<UserDailyTask> getDailyTasks(@RequestParam String userId) {
        return userDailyTaskService.getDailyTasksByUserId(userId);
    }

    @PostMapping("/updateTasksByTaskId")
    public void updateTaskProgress(@RequestParam String taskId, @RequestParam int progress) {
        userDailyTaskService.updateTaskProgress(taskId, progress);
    }
    @PostMapping("/updateByUserIdAndTag")
    public String updateByUserIdAndTag(@RequestParam String userId, @RequestParam String tag) {
        return userDailyTaskService.upupdateByUserIdAndTag(userId, tag);
    }
}
