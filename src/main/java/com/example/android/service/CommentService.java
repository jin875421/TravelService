package com.example.android.service;

import com.example.android.entity.*;
import com.example.android.repository.CommentRepository;
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

    //上传评论，将评论内容保存到数据库中
    public Comment addComment(Comment comment){
        Comment save = commentRepository.save(comment);
        return save;
    }

    //获取要显示的评论的信息，将评论内容和用户信息结合起来
    public List<ReturnComment> getReturnCommentList(String postId){
        List<ReturnComment> returnCommentList = new ArrayList<>();
        sort = Sort.by(Sort.Direction.DESC, "time");
        List<Comment> commentList = commentRepository.findByPostId(postId, sort);
        //遍历帖子下的所有评论内容
        for(Comment comment:commentList) {
            if (comment.getParentId() != null)
                continue;
            List<ReturnCommentRespond> returnCommentRespondList = new ArrayList<>();
            //遍历每条评论的回复内容
            List<Comment> CommentRespondList = commentRepository.findByParentId(comment.getCommentId(), sort);
            for(Comment commentRespond:CommentRespondList) {
                ReturnCommentRespond returnCommentRespond = new ReturnCommentRespond(
                        getUserNameByUserId(commentRespond.getUserId()),
                        commentRespond.getText(),
                        commentRespond.getTime(),
                        getAvatarByUserId(commentRespond.getUserId())
                );
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
            returnCommentList.add(returnComment);
        }
        return returnCommentList;
    }

    public List<ReturnCommentRespond> getReturnCommentRespondList(String theCommentId) {
        List<ReturnCommentRespond> returnCommentRespondList = new ArrayList<>();
        sort = Sort.by(Sort.Direction.DESC, "time");
        List<Comment> commentRespondList = commentRepository.findByParentId(theCommentId, sort);
        for(Comment commentRespond:commentRespondList) {
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
        String userName = userInfo.getUserName();
        return userName;
    }

    //查询头像
    public String getAvatarByUserId(String userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        String avatar = userInfo.getAvatar();
        return avatar;
    }

}
