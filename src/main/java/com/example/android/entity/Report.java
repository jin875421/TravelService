package com.example.android.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Report {
    @Id
    private String reportId;
    private String postId;
    private String userId;
    private String reportReason;
    private String reportTime;
    private int reportStatus;

    public Report(String postId, String userId, String reason, String time) {
        this.postId = postId;
        this.userId = userId;
        this.reportReason = reason;
        this.reportTime = time;
        this.reportId = UUID.randomUUID().toString();
        this.reportStatus = 0;
    }

    public Report() {

    }
}
