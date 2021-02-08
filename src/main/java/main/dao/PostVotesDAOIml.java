package main.dao;

import main.entity.*;
import main.repo.PostRepository;
import main.repo.PostVotesRepository;
import main.repo.UserRepository;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Date;

public class PostVotesDAOIml implements PostVotesDAO{
    private final UserRepository userRepository;
    private final PostVotesRepository postVotesRepository;
    private final PostRepository postRepository;
    private JdbcTemplate jdbcTemplate;
    final String INSERT_QUERY = "insert into posts_votes (post_id, votes_id) values (4, 12);";
    @Autowired
    public PostVotesDAOIml(UserRepository userRepository, PostVotesRepository postVotesRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postVotesRepository = postVotesRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void save(String postId, String email) throws SQLException {
        try {
            Integer postIdPars = Integer.parseInt(postId.replaceAll("\\D",""));
            main.entity.User user = userRepository.findByEmail(email).get();
            Post post = postRepository.findById(postIdPars).get();
            PostVotes postVotes = new PostVotes();
            Date date = new Date();
            postVotes.setPost_id(post);
            postVotes.setPost(post);
            postVotes.setUser(user);
            postVotes.setUser_id(user);
            postVotes.setTime(date);
            byte z = 1;
            postVotes.setValue(z);
            System.out.println(postIdPars.toString() + " " +Integer.toString(postVotes.getId()) + "-------");
            jdbcTemplate.update(INSERT_QUERY,post.getId(),postVotes.getId());
            postVotesRepository.save(postVotes);
//            int id = postVotesRepository.getId(user.getId(),date);

        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
    @Override
    public void saveDislike(String postId,String email) throws SQLException{
        Integer postIdPars = Integer.parseInt(postId.replaceAll("\\D",""));
        main.entity.User user = userRepository.findByEmail(email).get();
        Post post = postRepository.findById(postIdPars).get();
        PostVotes postVotes = new PostVotes();
        Date date = new Date();
        postVotes.setPost_id(post);
        postVotes.setPost(post);
        postVotes.setUser(user);
        postVotes.setUser_id(user);
        postVotes.setTime(date);
        byte z = -1;
        postVotes.setValue(z);
        System.out.println(postIdPars.toString() + " " +Integer.toString(postVotes.getId()) + "-------");
        jdbcTemplate.update(INSERT_QUERY,post.getId(),postVotes.getId());
        postVotesRepository.save(postVotes);
    }

    @Override
    public void delete(String postId, String email) {

    }
}
