package com.example.android.entity;

public class Result {
    private String keyword;
    private float score;
    private String root;
    private baike_info baike_info;

    public Result() {
    }

    public Result(String keyword, float score, String root, com.example.android.entity.baike_info baike_info) {
        this.keyword = keyword;
        this.score = score;
        this.root = root;
        this.baike_info = baike_info;
    }

    @Override
    public String toString() {
        return "Result{" +
                "keyword='" + keyword + '\'' +
                ", score=" + score +
                ", root='" + root + '\'' +
                ", baike_info=" + baike_info +
                '}';
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public com.example.android.entity.baike_info getBaike_info() {
        return baike_info;
    }

    public void setBaike_info(com.example.android.entity.baike_info baike_info) {
        this.baike_info = baike_info;
    }
}
