package main.service;

import main.Entity.PostRepository;

import main.api.response.PostResponse;

import main.base.Post;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    public PostResponse getPosts(int offset, int limit, String mode){
        List<Post> posts = new ArrayList<>();
        try {
            List<main.Entity.Post> posts1 = postRepository.getRecentPosts();
            for (int i = 1; i < posts1.size() - 1; i++){
               /* posts.get(i).setAnnounce(posts1.get(i).getText().toString());
                posts.get(i).setViewCount(posts1.get(i).getView_count().get());
                posts.get(i).getUser().setName(posts1.get(i).getUser().getName());
                posts.get(i).getUser().setId(posts1.get(i).getUser().getId());
                posts.get(i).setTimestamp(posts1.get(i).getTime().getTime());
                posts.get(i).setId(posts1.get(i).getId());

                */
                PostResponse postResponse = new PostResponse();
                postResponse.setPosts(posts1);
                postResponse.setCount(posts1.size() - 1);
                return postResponse;

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;


    }
}
// PostResponse postResponse = new PostResponse();
//  User user = new User();
//   user.setId(92);
//         user.setName("Андрей Иванов");
//        Post post = new Post();
//       post.setAnnounce("Текст анонса");
//      post.setDislikeCount(4);
//     post.setId(56);
//     post.setLikeCount(92);
//    post.setTimestamp(1592338706);
//    post.setTitle("Hello world!");
//   post.setUser(user);
//  post.setViewCount(1005);
//  postResponse.setCount(2);
//   posts.add(post);