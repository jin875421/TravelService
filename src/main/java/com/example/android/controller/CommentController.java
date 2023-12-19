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
        logger.info("移动端请求获取帖子评论列表,postId="+thePostId);
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
        System.out.println("移动端在评论详情页面上提交回复之后请求刷新数据，服务器返回的数据"+json);

        return json;
    }

    @PostMapping("/addComment")
    public String addComment(@RequestBody Comment comment) {
        System.out.println("接受到的评论内容"+comment.toString());
        service.addComment(comment);

        return "评论接收成功";
    }

}
