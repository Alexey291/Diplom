package main.dao;

import main.entity.*;
import main.repo.PostRepository;
import main.repo.PostVotesRepository;
import main.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    public Boolean save(String postId, String email, User user) throws SQLException {
        try {
            int postIdInt = Integer.parseInt(postId.replaceAll("\\D",""));
            Post post = postRepository.findById(postIdInt);
            AtomicInteger likeCount = new AtomicInteger(0);
            List<PostVotes> list = post.getVotes();
            AtomicBoolean likeExist = new AtomicBoolean(false);
            AtomicBoolean disLikeExist = new AtomicBoolean(false);
            list.forEach(postVotes -> {
                if (postVotes.getUser().getId() == user.getId()){
                    if (postVotes.getValue() == -1){
                        postVotes.setValue((byte) 1);
                        disLikeExist.set(true);
                    }
                    else
                    { likeExist.set(true);}
                }
                else if (postVotes.getValue() == 1){
                    likeCount.incrementAndGet();
                }
            });
            if (likeExist.get()){
                return false;
            }
            else if (disLikeExist.get()){
                post.setVotes(list);
                postRepository.save(post);
                return true;
            }
            else {
                PostVotes postVotes = new PostVotes();
                postVotes.setValue((byte) 1);
                postVotes.setTime(new Date());
                postVotes.setUser_id(user);
                postVotes.setUser(user);
                postVotes.setPost_id(post);
                list.add(postVotes);
                post.setVotes(list);
                postRepository.save(post);
                return true;}
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }
    @Override
    public Boolean saveDislike(String postId,String email, User user) throws SQLException{
        try {
            int postIdInt = Integer.parseInt(postId.replaceAll("\\D",""));
            Post post = postRepository.findById(postIdInt);
            AtomicInteger likeCount = new AtomicInteger(0);
            List<PostVotes> list = post.getVotes();
            AtomicBoolean disLikeExist = new AtomicBoolean(false);
            AtomicBoolean likeExist = new AtomicBoolean(false);
            list.forEach(postVotes -> {
                if (postVotes.getUser().getId() == user.getId()){
                    if (postVotes.getValue() == 1){
                        postVotes.setValue((byte) -1);
                        likeExist.set(true);
                    }
                    else
                    { disLikeExist.set(true);}
                }
                if (postVotes.getValue() == -1){
                    likeCount.incrementAndGet();
                }
            });
            if (disLikeExist.get()){
                return false;
            }
            else if (likeExist.get()){
                post.setVotes(list);
                postRepository.save(post);
                return true;
            } else {
            PostVotes postVotes = new PostVotes();
            postVotes.setValue((byte) -1);
            postVotes.setTime(new Date());
            postVotes.setUser_id(user);
            postVotes.setUser(user);
            postVotes.setPost_id(post);
            list.add(postVotes);
            post.setVotes(list);
            postRepository.save(post);
            return true;}

        }catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(String postId, String email) {

    }
}
