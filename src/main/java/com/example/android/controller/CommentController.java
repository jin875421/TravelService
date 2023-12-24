package com.example.android.controller;

import com.example.android.entity.Comment;
import com.example.android.entity.ReturnComment;
import com.example.android.entity.ReturnCommentRespond;
import com.example.android.service.CommentService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private static Logger logger = Logger.getLogger(CommentController.class);

    @Autowired
    CommentService service;
    @PostMapping("/getReturnCommentList")
    public String getReturnComment(@RequestBody String postId) {
        String thePostId = postId;
        List<ReturnComment> returnCommentList;
        returnCommentList = service.getReturnCommentList(thePostId);
        Gson gson = new Gson();
        String json = gson.toJson(returnCommentList);

        return json;
    }
    @GetMapping("/getCommentCount")
    public int getCommentCount(String postId){
        return service.getReturnCommentList(postId).size();
    }
    @PostMapping("/getReturnCommendRespondList")
    public String getCommendRespond(@RequestBody String commentId) {
        String theCommentId = commentId;
        List<ReturnCommentRespond> returnCommentRespondList;
        returnCommentRespondList = service.getReturnCommentRespondList(theCommentId);
        Gson gson = new Gson();
        String json = gson.toJson(returnCommentRespondList);

        return json;
    }

    @PostMapping("/addComment")
    public String addComment(@RequestBody Comment comment) {
        service.addComment(comment);
        return "评论接收成功";
    }

}
