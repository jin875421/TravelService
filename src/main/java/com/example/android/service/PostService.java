package com.example.android.service;

import com.example.android.entity.Post;
import com.example.android.entity.PostContent;
import com.example.android.entity.PostItem;
import com.example.android.entity.PostPicture;
import com.example.android.repository.PostContentRepository;
import com.example.android.repository.PostPictureRepository;
import com.example.android.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostContentRepository postContentRepository;
    @Autowired
    private PostPictureRepository postPictureRepository;
    private Sort sort;
    public void createPost(PostItem post){
        System.out.println(post.getUserId());
        post.setPostId(UUID.randomUUID().toString());
        postRepository.save(new Post(post.getPostId(),post.getUserId(),post.getCreateTime()));
        for(String picturePath:post.getPicturePath()){
            postPictureRepository.save(new PostPicture(UUID.randomUUID().toString(),picturePath,post.getPostId()));
        }
        postContentRepository.save(new PostContent(UUID.randomUUID().toString(),post.getPostId(),post.getPostContent(),post.getPostTitle(),post.getPictureNumber()));
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
            PostItem postItem = new PostItem(post.getPostId(),postContent.getPostTitle(),postContent.getPostContent(),post.getUserId(),post.getCreateTime(),picturePaths,postContent.getPictureNumber());
            postItems.add(postItem);
        }
        return postItems;
    }
}
