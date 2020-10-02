package main.service;

import main.base.Post;
import main.api.response.PostResponse;
import main.base.User;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    public PostResponse getPosts(){
        User user = new User();
        user.setId(92);
        user.setName("Андрей Иванов");
        Post post = new Post();
        post.setAnnounce("Текст анонса");
        post.setDislikeCount(4);
        post.setId(56);
        post.setLikeCount(92);
        post.setTimestamp(1592338706);
        post.setTitle("Hello world!");
        post.setUser(user);
        post.setViewCount(1005);
        PostResponse postResponse = new PostResponse();
        postResponse.setCount(1);
        postResponse.setPost(post);
        return postResponse;
    }
}
