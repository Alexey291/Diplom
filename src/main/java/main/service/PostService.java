package main.service;


import com.fasterxml.jackson.annotation.JsonProperty;
import main.Entity.PostRepository;

import main.api.response.PostResponse;

import main.Entity.Post;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    public PostResponse getPosts(int offset, int limit, String mode){
        try {
            List<Post> posts1 = postRepository.getRecentPosts();
            PostResponse postResponse = new PostResponse();
            postResponse.setPosts(posts1);
            postResponse.setCount(posts1.size() - 1);
            return postResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Post getOnePost(int id){
        return  postRepository.findById(id);
    }
}
