package main.service;


import main.entity.*;

import main.api.response.PostResponse;

import main.base.PostForResponceById;
import main.base.PostListResponse;
import main.base.UserPostResponse;
import main.repo.PostRepository;
import main.repo.TagsRepository;
import main.repo.UserRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.*;
import java.util.*;


@Service
public class PostService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    TagsRepository tagsRepository;
    public PostResponse getPosts(int offset, int limit, String mode){
        try {

            Pageable pageable = PageRequest.of(offset / limit, limit);
            PostResponse postResponse = new PostResponse();
            Page <Post> posts = Page.empty();
            switch (mode){
                case "recent":
                    posts = postRepository.getPostsNewDateWithPagination(pageable,Status.ACCEPTED);
                    break;
                case "popular":
                    posts = postRepository.getPostsPopWithPagination(pageable);
                    break;
                case "early":
                    posts = postRepository.getPostsOldDateWithPagination(pageable);
                    break;
                case "best":
                    posts = postRepository.getPostsBestWithPagination(pageable);
                    break;
            }

            List<PostListResponse> newPosts = new ArrayList<>();
            List<PostListResponse> newPostsPut = getPostList(posts,newPosts);
            postResponse.setPosts(newPostsPut);
            postResponse.setCount((int)posts.getTotalElements());
            return postResponse;

        }catch (Exception e){
            e.printStackTrace();}
        return new PostResponse();
    }
    public PostForResponceById getPost(int id){
        try {

            int countView = postRepository.viewCount(id) + 1;
            postRepository.updateViewCount(countView,id);
            List<Tags> listTag = tagsRepository.getTagsForPost(id);
            List<String> finalListTag = new ArrayList<>();
            for (Tags tag : listTag) {
                finalListTag.add(tag.getName());
            }
            Post post = postRepository.findById(id);
            PostForResponceById newPost = new PostForResponceById();
            newPost.setActive(post.getIsActive());
            newPost.setComments(post.getCommentsResponce());
            newPost.setId(post.getId());
            newPost.setDislikeCount((int)post
                    .getVotes()
                    .stream()
                    .filter(dis -> dis.getValue() == -1)
                    .count());
            newPost.setLikeCount((int)post
                    .getVotes()
                    .stream()
                    .filter(like -> like.getValue() == 1)
                    .count());
            newPost.setText(post.getText());
            newPost.setTimestamp(post.getTimeForFront());
            newPost.setTitle(post.getTitle());
            newPost.setViewCount(post.getViewCount());
            User author = post.getUser();
            newPost.setUser(new UserPostResponse(author.getId(),author.getName()));
            newPost.setTags(finalListTag);
            return  newPost;}
        catch (Exception e){
            e.printStackTrace();
        }
        return new PostForResponceById();
    }
    public PostResponse getPostQuery(int offset, int limit, String query){
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Post> posts = postRepository.findPostsWithPartOfTextWithPagination(query,pageable);
        List<PostListResponse> newPosts = new ArrayList<>();
        List<PostListResponse> newPostsPut = getPostList(posts,newPosts);
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(newPostsPut);
        postResponse.setCount((int)posts.getTotalElements());
        return postResponse;
    }
    public PostResponse getPostByDate(int offset, int limit, LocalDate date){
        Pageable pageable = PageRequest.of(offset / limit, limit,Sort.by("time_post").descending());
        Page<Post> posts = postRepository.getPostDateWithPagination(1,Status.ACCEPTED.name(),
                Date.from(date.minusDays(1).atTime(23, 59,59).toInstant(ZoneOffset.UTC)),
                Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()),pageable);
        List<PostListResponse> newPost = new ArrayList<>();
        List<PostListResponse> newPostsPut = getPostList(posts,newPost);
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(newPostsPut);
        postResponse.setCount((int)posts.getTotalElements());
        return postResponse;
    }
    public PostResponse getPostByTag(int offset, int limit, String tag){
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Post> posts = postRepository.getPostTagWithPagination(1,Status.ACCEPTED.name(),tag,pageable);
        List<PostListResponse> newPosts = new ArrayList<>();
        try {
            List<PostListResponse> newPostsPut = getPostList(posts,newPosts);
            PostResponse postResponse = new PostResponse();
            postResponse.setPosts(newPostsPut);
            postResponse.setCount((int)posts.getTotalElements());
            return postResponse;

        }catch (Exception ex){
            ex.printStackTrace();
            PostResponse postResponse = new PostResponse();
            postResponse.setCount(0);
            return postResponse;
        }

    }
    public void PostUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        User user1 = new main.entity.User();
        user1.setCode(user.getCode());
        user1.setEmail(user.getEmail());
        user1.setIs_moderator(false);
        user1.setName(user.getName());
        user1.setRegTime(new Date());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
    }
   /* public void putLike(Integer id, Integer userId){
        postRepository.updateLike(id,new Date(),userId);
    }

    */

    public static List<PostListResponse> getPostList(Page<Post> posts,List<PostListResponse> newPosts){
        for (Post post : posts) {
            PostListResponse postListResponse = new PostListResponse();
            String textWithoutTags = Jsoup.parse(post.getText()).text();
            postListResponse.setId(post.getId());
            postListResponse.setAnnounce(textWithoutTags.substring(0, Math.min(15, textWithoutTags.length())));
            postListResponse.setTimestamp(post.getTime().getTime() / 1000);
            postListResponse.setTitle(post.getTitle());
            postListResponse.setViewCount(post.getViewCount());

            User author = post.getUser();
            postListResponse.setUser(new UserPostResponse(author.getId(), author.getName()));

            postListResponse.setDislikeCount((int) post
                    .getVotes()
                    .stream()
                    .filter(v -> v.getValue() == -1)
                    .count());

            postListResponse.setLikeCount((int) post
                    .getVotes()
                    .stream()
                    .filter(v -> v.getValue() == 1)
                    .count());

            postListResponse.setCommentCount(post.getCommentsCount());
            newPosts.add(postListResponse);}
        return newPosts;
    }
    public PostResponse getMyPosts(int offset, int limit, String status, int userId){
        try {
            Pageable pageable = PageRequest.of(offset / limit, limit);
            PostResponse postResponse = new PostResponse();
            Page <Post> posts = Page.empty();
            switch (status){
                case "inactive":
                    posts = postRepository.getPostsWithPaginationForAuthUser(pageable,userId);
                    break;
                case "pending":
                    posts = postRepository.getPostsWithPaginationPendingForAuth(pageable,userId);
                    break;
                case "declined":
                    posts = postRepository.getPostsWithPaginationDeclinedForAuth(pageable,userId);
                    break;
                case "published":
                    posts = postRepository.getPostsWithPaginationPublishedForAuth(pageable,userId);
                    break;
            }

            List<PostListResponse> newPosts = new ArrayList<>();
            List<PostListResponse> newPostsPut = getPostList(posts,newPosts);
            postResponse.setPosts(newPostsPut);
            postResponse.setCount((int)posts.getTotalElements());
            return postResponse;

        }catch (Exception e){
            e.printStackTrace();}
        return new PostResponse();
    }

}
