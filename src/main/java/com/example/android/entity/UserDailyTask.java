package com.example.android.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_daily_task")
@Data
public class UserDailyTask {
    public static final int DAILY_TASK_TYPE_DAILY = 0;
    public static final int DAILY_TASK_TYPE_WEEKLY = 1;
    public static final int DAILY_TASK_TYPE_MONTHLY = 2;
    public static final int DAILY_TASK_TYPE_ONCE = 3;

    @Id
    @Column(name = "id", length = 100, nullable = false)
    private String id;

    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

    @Column(name = "task_name", length = 255, nullable = false)
    private String taskName;

    @Column(name = "progress", columnDefinition = "int default 0")
    private int progress;

    @Column(name = "max_progress", nullable = false)
    private int maxProgress;

    @Column(name = "reward", nullable = false)
    private int reward;

    @Column(name = "completed", columnDefinition = "tinyint(1) default 0")
    private boolean completed;

    @Column(name = "last_updated", nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Timestamp lastUpdated;

    @Column(name = "type" , nullable = false, columnDefinition = "int default 0")
    private int type;

    @Column(name = "task_icon", length = 100)
    private String taskIcon;

    public UserDailyTask() {
    }
    public UserDailyTask(String id, String userId, String taskName, int progress, int maxProgress, int reward, boolean completed, Timestamp lastUpdated, int type, String taskIcon) {
        this.id = id;
        this.userId = userId;
        this.taskName = taskName;
        this.progress = progress;
        this.maxProgress = maxProgress;
        this.reward = reward;
        this.completed = completed;
        this.lastUpdated = lastUpdated;
        this.type = type;
        this.taskIcon = taskIcon;
    }

    public boolean getCompleted() {
        return completed;
    }
}

