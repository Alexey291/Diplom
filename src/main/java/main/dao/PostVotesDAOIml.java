package main.dao;

import main.entity.*;
import main.repo.PostRepository;
import main.repo.PostVotesRepository;
import main.repo.UserRepository;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PostVotesDAOIml implements PostVotesDAO{
    private final UserRepository userRepository;
    private final PostVotesRepository postVotesRepository;
    private final PostRepository postRepository;


    @Autowired
    public PostVotesDAOIml(UserRepository userRepository, PostVotesRepository postVotesRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postVotesRepository = postVotesRepository;
        this.postRepository = postRepository;
    }



    @Override
    public void save(String postId, String email, User user) throws SQLException {
        try {
            int postIdInt = Integer.parseInt(postId.replaceAll("\\D",""));
            Post post = postRepository.findById(postIdInt);
            AtomicInteger likeCount = new AtomicInteger(0);
            List<PostVotes> list = post.getVotes();
            list.forEach(postVotes -> {
                if (postVotes.getValue() == 1){
                    likeCount.incrementAndGet();
                }
            });
            PostVotes postVotes = new PostVotes();
            postVotes.setValue((byte) 1);
            postVotes.setTime(new Date());
            postVotes.setUser_id(user);
            postVotes.setUser(user);
            postVotes.setPost_id(post);
            list.add(postVotes);
            post.setVotes(list);
            postRepository.save(post);

        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
    @Override
    public void saveDislike(String postId,String email, User user) throws SQLException{
        try {
            int postIdInt = Integer.parseInt(postId.replaceAll("\\D",""));
            Post post = postRepository.findById(postIdInt);
            AtomicInteger likeCount = new AtomicInteger(0);
            List<PostVotes> list = post.getVotes();
            list.forEach(postVotes -> {
                if (postVotes.getValue() == -1){
                    likeCount.incrementAndGet();
                }
            });
            PostVotes postVotes = new PostVotes();
            postVotes.setValue((byte) -1);
            postVotes.setTime(new Date());
            postVotes.setUser_id(user);
            postVotes.setUser(user);
            postVotes.setPost_id(post);
            list.add(postVotes);
            post.setVotes(list);
            postRepository.save(post);

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(String postId, String email) {

    }
}
