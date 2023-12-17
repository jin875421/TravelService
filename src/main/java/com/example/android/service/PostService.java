package com.example.android.service;

import com.example.android.entity.*;
import com.example.android.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostContentRepository postContentRepository;
    @Autowired
    private PostPictureRepository postPictureRepository;
    @Autowired
    private StarRepository starRepository;
    private Sort sort;
    public boolean starexist(String userId,String postId){
        if (starRepository.findByPostIdAndUserId(postId,userId)!=null){
            return true;
        }else return false;

    }
    //更新post(不添加图片)

    public void updatePostWithoutPicture(PostItem post,String postId){

        //获取contentId
        postContentRepository.save(new PostContent(postContentRepository.findByPostId(postId).getContentId()
                ,postId,post.getPostContent(),post.getPostTitle()));

    }
    @Transactional
    public void deleteimage(List<String> delete){
        //删除图片
        for (String path:delete){
            postPictureRepository.deleteByPicturePath(path.replaceAll("[\\[\\]\"]", ""));
        }
    }
    //更新post(添加图片)
    @Transactional
    public void updatePost(PostItem post,String postId){
        //更新post
        for(String picturePath:post.getPicturePath()){
            postPictureRepository.save(new PostPicture(UUID.randomUUID().toString(),picturePath,postId));
        }
        postContentRepository.save(new PostContent(postContentRepository.findByPostId(postId).getContentId()
                ,postId,post.getPostContent(),post.getPostTitle()));
    }
    public void saveStar(String postId,String userId){

        starRepository.save(new Star(postId,userId,UUID.randomUUID().toString()));
    }
    @Transactional
    public void deletePost(String postId){
        postRepository.deleteById(postId);
        postContentRepository.deleteByPostId(postId);
        for (PostPicture postPicture:postPictureRepository.findByPostId(postId)){
            postPictureRepository.deleteById(postPicture.getPictureId());
        }
        commentRepository.deleteByPostId(postId);
    }
    public List<PostItem> searchPost(String searchText){
        List<Post> postList = postRepository.findAll(sort);
        List<PostItem> postItems = new ArrayList<>();
        for (Post post:postList){
            if (postContentRepository.findByPostId(post.getPostId()).getPostTitle().contains(searchText)){
                List<String> picturePaths = new ArrayList<>();
                for (PostPicture postPicture:postPictureRepository.findByPostId(post.getPostId())){
                    picturePaths.add(postPicture.getPicturePath());
                }
                PostContent postContent = postContentRepository.findByPostId(post.getPostId());
                PostItem postItem = new PostItem(post.getPostId(),postContent.getPostTitle(),postContent.getPostContent(),post.getUserId(),post.getCreateTime(),picturePaths);
                postItems.add(postItem);
            }
        }
        return postItems;
    }
    public void createPost(PostItem post){
        post.setPostId(UUID.randomUUID().toString());
        postRepository.save(new Post(post.getPostId(),post.getUserId(),post.getCreateTime()));
        for(String picturePath:post.getPicturePath()){
            postPictureRepository.save(new PostPicture(UUID.randomUUID().toString(),picturePath,post.getPostId()));
        }
        postContentRepository.save(new PostContent(UUID.randomUUID().toString(),post.getPostId(),post.getPostContent(),post.getPostTitle()));
    }
    public List<PostItem> findPostList(){
        List<Post> postList;
        List<PostItem> postItems = new ArrayList<>();
        sort = Sort.by(Sort.Direction.DESC, "createTime");
        postList = postRepository.findAll(sort);
        for (Post post:postList){
            List<String> picturePaths = new ArrayList<>();
            for (PostPicture postPicture:postPictureRepository.findByPostId(post.getPostId())){
                picturePaths.add(postPicture.getPicturePath());
            }
            PostContent postContent = postContentRepository.findByPostId(post.getPostId());
            PostItem postItem = new PostItem(post.getPostId(),postContent.getPostTitle(),postContent.getPostContent(),post.getUserId(),post.getCreateTime(),picturePaths);
            postItems.add(postItem);
        }
        return postItems;
    }

    public List<PostItem> findStarList(String userId) {
        List<Post> postList;
        List<PostItem> postItems = new ArrayList<>();
        List<String> postId = new ArrayList<>();
        postId = starRepository.findPostIdByUserId(userId);
//        sort = Sort.by(Sort.Direction.DESC, "createTime");
        postList = postRepository.findAllById(postId);
        for (Post post:postList){
            List<String> picturePaths = new ArrayList<>();
            for (PostPicture postPicture:postPictureRepository.findByPostId(post.getPostId())){
                picturePaths.add(postPicture.getPicturePath());
            }
            PostContent postContent = postContentRepository.findByPostId(post.getPostId());
            PostItem postItem = new PostItem(post.getPostId(),postContent.getPostTitle(),postContent.getPostContent(),post.getUserId(),post.getCreateTime(),picturePaths);
            postItems.add(postItem);
        }
        return postItems;
    }

    public PostItem findById(String postId) {
        Post post = postRepository.findById(postId).get();
        PostItem postItem = new PostItem();
        if (postContentRepository.findByPostId(postId)!=null){
            List<String> picturePaths = new ArrayList<>();
            for (PostPicture postPicture:postPictureRepository.findByPostId(postId)){
                picturePaths.add(postPicture.getPicturePath());
            }
            PostContent postContent = postContentRepository.findByPostId(postId);
            postItem = new PostItem(post.getPostId(),postContent.getPostTitle(),postContent.getPostContent(),post.getUserId(),post.getCreateTime(),picturePaths);
        }
        return postItem;
    }
}
