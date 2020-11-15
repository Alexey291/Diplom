package main.service;


import main.entity.*;

import main.api.response.PostResponse;

import main.base.PostForResponceById;
import main.base.PostListResponse;
import main.base.UserPostResponse;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostService {
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
            List<Tags> listTag = tagsRepository.getTagsForPost(id);
            List<String> finalListTag = new ArrayList<>();
            for (Tags tag : listTag) {
                finalListTag.add(tag.getName());
            }

            Post post = postRepository.findById(id);
            PostForResponceById newPost = new PostForResponceById();
            newPost.setActive(post.getIs_active());
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
            newPost.setViewCount(post.getView_count());
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
    public static List<PostListResponse> getPostList(Page<Post> posts,List<PostListResponse> newPosts){
        for (Post post : posts) {
            PostListResponse postListResponse = new PostListResponse();
            String textWithoutTags = Jsoup.parse(post.getText()).text();
            postListResponse.setId(post.getId());
            postListResponse.setAnnounce(textWithoutTags.substring(0, Math.min(15, textWithoutTags.length())));
            postListResponse.setTimestamp(post.getTime().getTime() / 1000);
            postListResponse.setTitle(post.getTitle());
            postListResponse.setViewCount(post.getView_count());

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

            postListResponse.setCommentCount(post.getCommentsCount());//-----------------
            newPosts.add(postListResponse);}
        return newPosts;
    }

}
