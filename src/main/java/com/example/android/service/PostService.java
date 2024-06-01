package com.example.android.service;

import com.example.android.entity.*;
import com.example.android.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
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
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    private Sort sort;
    public boolean starExist(String userId,String postId){
        if (starRepository.findByPostIdAndUserId(postId,userId)!=null){
            return true;
        }else return false;
    }
    public boolean likeExist(String userId,String postId){
        if (likeRepository.findByPostIdAndUserId(postId,userId)!=null){
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
    public void deleteStar(String postId,String userId){
        starRepository.deleteByPostIdAndUserId(postId,userId);
    }
    //添加点赞
    public void saveLike(String postId,String userId){
        likeRepository.save(new Likes(postId,userId,UUID.randomUUID().toString()));
    }
    //删除点赞
    public void deleteLike(String postId,String userId){
        likeRepository.deleteByPostIdAndUserId(postId,userId);
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
        sort = Sort.by(Sort.Direction.DESC, "createTime");
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
    public List<PostItem> findPostList(Pageable page){
        List<Post> postList;
        List<PostItem> postItems = new ArrayList<>();
//        sort = Sort.by(Sort.Direction.DESC, "createTime");
        postList = postRepository.findAll(page).getContent();
        for (Post post:postList){
            List<String> picturePaths = new ArrayList<>();
            for (PostPicture postPicture:postPictureRepository.findByPostId(post.getPostId())){
                picturePaths.add(postPicture.getPicturePath());
            }
            PostContent postContent = postContentRepository.findByPostId(post.getPostId());
            System.out.println(post.getPostId()+"dsadasdasd");
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
    public List<PostItem> findMyPostList(String userId) {
        List<Post> postList;
        List<PostItem> postItems = new ArrayList<>();
        postList = postRepository.findByUserId(userId);
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

    public int getLikeCount(String postId) {
        return likeRepository.countByPostId(postId);
    }

    public void reportPost(Report report) {
        reportRepository.save(report);
    }

    /**
     * 获取指定用户的关注列表。
     *
     * @param userId 需要获取关注列表的用户ID。
     * @return 返回该用户的关注列表，包含有效的关注记录。
     */
    public List<Follow> getFollowList(String userId) {
        // 通过用户ID查询关注列表
        List<Follow> followList = followRepository.findByUserId(userId);

        // 遍历关注列表，验证关注的用户是否存在，若不存在则删除该关注记录
        followList.forEach(follow -> {
            UserInfo userInfo = userInfoRepository.findByUserId(follow.getFollowId());
            if (userInfo == null){
                followRepository.deleteByFollowId(follow.getFollowId());
                System.out.println("删除关注记录"+follow.getFollowId());
            }
        });

        // 重新获取经过验证后的关注列表
        followList = followRepository.findByUserId(userId);
        return followList;
    }

    /**
     *
     * @param userId
     * @param followId
     * @return 0: 成功 1:用户不存在 2:已关注
     */
    public int saveFollow(String userId, String followId) {
        //先检测关注的对象是否存在
        UserInfo userInfo = userInfoRepository.findByUserId(followId);
        if (userInfo == null){
            throw new RuntimeException("关注对象不存在");
        }
        //先检测是否已经关注过
        Follow follow = followRepository.findByUserIdAndFollowId(userId,followId);
        if(follow != null){
            return 2;
        }
        //未关注
        //动态生成UUID
        String id= UUID.randomUUID().toString();
        followRepository.save(new Follow(id,userId,followId));
        return 0;
    }

    /**
     * 删除用户的关注记录
     * @param userId 被关注者的用户ID
     * @param followId 关注者的用户ID
     * 该方法首先根据给定的userId和followId查找对应的关注记录，如果找到，则删除该记录。
     */
    public void deleteFollow(String userId,String followId) {
        // 根据userId和followId查找关注记录
        Follow follow =followRepository.findByUserIdAndFollowId(userId,followId);
        if(follow != null){
            // 如果找到关注记录，则根据id删除该记录
            followRepository.deleteById(follow.getId());
        }
    }

    /**
     * 检查某个用户是否已经关注了另一个用户。
     *
     * @param userId 用户ID，表示需要检查是否关注的用户的ID。
     * @param followId 被关注用户的ID。
     * @return 返回一个布尔值，如果指定用户已经关注了被关注用户，则返回true；否则返回false。
     */
    public Boolean followExist(String userId, String followId) {
        // 通过用户ID和被关注用户ID查询关注关系
        Follow follow =followRepository.findByUserIdAndFollowId(userId,followId);
        // 如果查询结果不为空，表示关注关系存在，返回true
        if(follow != null) return true;
        // 查询结果为空，表示关注关系不存在，返回false
        return false;
    }
}
