package com.example.android.service;

import com.example.android.entity.*;
import com.example.android.repository.CommentRepository;
import com.example.android.repository.CommentRespondRepository;
import com.example.android.repository.UserInfoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private Sort sort;
    private static Logger logger = Logger.getLogger(CommentService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private CommentRespondRepository commentRespondRepository;

    //上传评论，将评论内容保存到数据库中
    public Comment addComment(Comment comment){
        Comment save = commentRepository.save(comment);
        logger.info("保存到数据库中的评论"+save);
        return save;
    }

    //上传评论回复
    public CommentRespond addCommentRespond(CommentRespond commentRespond){
        CommentRespond save = commentRespondRepository.save(commentRespond);
        logger.info("保存到数据库中的评论回复"+save);
        return save;
    }

    //获取要显示的评论的信息，将评论内容和用户信息结合起来
    public List<ReturnComment> getReturnCommentList(String postId){
        System.out.println("service的getReturnCommentList执行");
        List<ReturnComment> returnCommentList = new ArrayList<>();
        sort = Sort.by(Sort.Direction.DESC, "time");
        List<Comment> commentList = commentRepository.findByPostId(postId, sort);
        System.out.println("commentList为"+commentList.size());
        //遍历帖子下的所有评论内容
        for(Comment comment:commentList) {
            List<ReturnCommentRespond> returnCommentRespondList = new ArrayList<>();
            //遍历每条评论的回复内容
            List<CommentRespond> CommentRespondList = commentRespondRepository.findByCommentId(comment.getCommentId(), sort);
            for(CommentRespond commentRespond:CommentRespondList) {
                ReturnCommentRespond returnCommentRespond = new ReturnCommentRespond(
                        getUserNameByUserId(commentRespond.getUserId()),
                        commentRespond.getText(),
                        commentRespond.getTime(),
                        getAvatarByUserId(commentRespond.getUserId())
                );
                System.out.println("returnCommentRespond"+returnCommentRespond);
                returnCommentRespondList.add(returnCommentRespond);
            }
            //将评论信息和发布者信息结合起来
            ReturnComment returnComment = new ReturnComment(comment.getCommentId(),
                    getUserNameByUserId(comment.getUserId()),
                    postId,
                    comment.getText(),
                    comment.getTime(),
                    getAvatarByUserId(comment.getUserId()),
                    comment.getUserId(),
                    returnCommentRespondList
            );
            System.out.println("returnComment为"+returnComment);
            returnCommentList.add(returnComment);
        }
        return returnCommentList;
    }

    public List<ReturnCommentRespond> getReturnCommentRespondList(String theCommentId) {
        List<ReturnCommentRespond> returnCommentRespondList = new ArrayList<>();
        sort = Sort.by(Sort.Direction.DESC, "time");
        List<CommentRespond> commentRespondList = commentRespondRepository.findByCommentId(theCommentId, sort);
        for(CommentRespond commentRespond:commentRespondList) {
            ReturnCommentRespond returnCommentRespond = new ReturnCommentRespond(
                    getUserNameByUserId(commentRespond.getUserId()),
                    commentRespond.getText(),
                    commentRespond.getTime(),
                    getAvatarByUserId(commentRespond.getUserId())
            );
            returnCommentRespondList.add(returnCommentRespond);
        }
        return returnCommentRespondList;
    }

    //查询用户名
    public String getUserNameByUserId(String userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        System.out.println("通过Id:"+userInfo.getUserId()+"查询到的用户名"+userInfo.getUserName());
        String userName = userInfo.getUserName();
        return userName;
    }

    //查询头像
    public String getAvatarByUserId(String userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        System.out.println("通过Id:"+userInfo.getUserId()+"查询到的头像"+userInfo.getAvatar());
        String avatar = userInfo.getAvatar();
        return avatar;
    }

}
