package main.service;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.Entity.PostRepository;

import main.Entity.User;
import main.api.response.PostResponse;

import main.Entity.Post;
import main.base.PostForResponceById;
import main.base.PostListResponse;
import main.base.UserPostResponse;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    public PostResponse getPosts(int offset, int limit, String mode){
        try {
            PostResponse postResponse = new PostResponse();
            List<Post> posts = postRepository.getRecentPosts();
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
            return postResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new PostResponse();
    }
    public PostForResponceById getOnePost(int id){
        try {
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
            return  newPost;}
        catch (Exception e){
            e.printStackTrace();
        }
        return new PostForResponceById();
    }
   /* public PostResponse getPostQuery(int offset, int limit, String query){
        List<Post> posts = postRepository.getRecentPosts();
        List<Post> findPost = new ArrayList<>();
        for (int post = 0; post < posts.size(); post++){
            if (posts.get(post).getText().contains(query)){
                findPost.add(post,posts.get(post));}
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(findPost);
        return postResponse;
    }

    */
}
