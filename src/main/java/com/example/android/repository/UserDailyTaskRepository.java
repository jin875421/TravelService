package com.example.android.repository;

import com.example.android.entity.UserDailyTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDailyTaskRepository extends JpaRepository<UserDailyTask, String> {
    List<UserDailyTask> findByUserId(String userId);

    UserDailyTask findByUserIdAndTaskName(String userId, String taskName);
}
