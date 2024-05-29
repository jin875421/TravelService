package com.example.android.entity;

import java.util.Arrays;

public class AIResult {
    private String log_id;
    private String result_num;
    private Result[] results;

    public AIResult(String log_id, String result_num, Result[] results) {
        this.log_id = log_id;
        this.result_num = result_num;
        this.results = results;
    }

    @Override
    public String toString() {
        return "AIResult{" +
                "log_id='" + log_id + '\'' +
                ", result_num='" + result_num + '\'' +
                ", results=" + Arrays.toString(results) +
                '}';
    }

    public AIResult() {
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getResult_num() {
        return result_num;
    }

    public void setResult_num(String result_num) {
        this.result_num = result_num;
    }

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }
}
