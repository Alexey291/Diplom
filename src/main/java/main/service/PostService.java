package main.service;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.Entity.*;

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
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    Tag2PostRepository tag2PostRepository;
    @Autowired
    TagsRepository tagsRepository;
    public PostResponse getPosts(int offset, int limit, String mode){
        try {

            Pageable pageable = PageRequest.of(offset / limit, limit,Sort.by("time_post").descending());
            Pageable pageableForPop = PageRequest.of(offset / limit, limit,Sort.by("view_count").descending());
            Pageable pageableForDate = PageRequest.of(offset / limit, limit,Sort.by("time_post"));
            PostResponse postResponse = new PostResponse();
            Page <Post> posts = Page.empty();
            switch (mode){
                case "recent":
                    posts = postRepository.getPostsWithPagination(pageable);
                    break;
                case "popular":
                    posts = postRepository.getPostsPopWithPagination(pageableForPop);
                    break;
                case "early":
                    posts = postRepository.getPostsDateWithPagination((pageableForDate));
                    break;
                case "best":
                    posts = postRepository.getPostsWithPagination(pageable);
                    break;
            }

            List<PostListResponse> newPosts = new ArrayList<>();
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

                newPosts.add(postListResponse);
            }
            postResponse.setPosts(newPosts);
            postResponse.setCount(postRepository.getRecentPost().size());
            return postResponse;

        }catch (Exception e){
            e.printStackTrace();}
        return new PostResponse();
    }
    public PostForResponceById getOnePost(int id){
        try {
            List<Tag2post> listTag2Post = tag2PostRepository.findAll().stream().filter(t -> t.getPost_id() == id).collect(Collectors.toList());
            List<Tags> listTag = tagsRepository.getRecentTags();
            List<String> finalListTag = new ArrayList<>();
            for (Tag2post tag2post : listTag2Post) {
               // System.out.println(tag2post.getId());
                for (Tags tag : listTag) {
                   // System.out.print(tag.getName());
                    if (tag.getId() == tag2post.getId()) {
                        finalListTag.add(tag.getName());
                    }
                }
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
        Pageable pageable = PageRequest.of(offset / limit, limit,Sort.by("time_post").descending());
        List<Post> posts = postRepository.getRecentPost();
        List<PostListResponse> newPosts = new ArrayList<>();
        //List<Post> findPost = new ArrayList<>();
        for (Post post : posts) {
            if (post.getText().contains(query)){
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
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(newPosts);
        return postResponse;
    }
    public PostResponse getPostByDate(int offset, int limit, LocalDate date){
        LocalDateTime date1 = date.atStartOfDay();
        List<Post> posts = postRepository.getRecentPost();
        List<PostListResponse> newPost = new ArrayList<>();
        for (Post post : posts) {
                LocalDateTime localDate = post.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
                if (localDate.isEqual(date1)){

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
            newPost.add(postListResponse);}
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(newPost);
        return postResponse;
    }
    public PostResponse getPostByTag(int offset, int limit, String tag){
        List<Post> posts = postRepository.getRecentPost();
        List<PostListResponse> newPosts = new ArrayList<>();
        Tags tag1 = tagsRepository.findByName(tag);
        try {
            Tag2post tag2Post = tag2PostRepository.findByTagId(tag1.getId());
            for (Post post : posts) {
                if (post.getId() == tag2Post.getPost_id()){
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
            }
            PostResponse postResponse = new PostResponse();
            postResponse.setPosts(newPosts);
            return postResponse;

        }catch (Exception ex){
            ex.printStackTrace();
            PostResponse postResponse = new PostResponse();
            postResponse.setCount(0);
            return postResponse;
        }

    }

}
