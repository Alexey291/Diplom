package main.dao;

import main.api.response.StatisticResponse;
import main.entity.Post;
import main.entity.User;
import main.repo.PostRepository;
import main.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class StatisticDAOImpl implements StatisticDAO{
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public StatisticDAOImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public StatisticResponse getStatisticUser(User user) {
        User user1 = userRepository.findByEmail(user.getEmail()).get();
        List<Post> posts = postRepository.findPostsByUser(user1.getId());
        StatisticResponse statisticResponse = new StatisticResponse();
        AtomicInteger dislike = new AtomicInteger(0);
        AtomicInteger like = new AtomicInteger(0);

        posts.forEach(post -> {
            post.getVotes().forEach( postVotes -> {
                if (postVotes.getValue() == -1){
                    dislike.incrementAndGet();
                }
                else {
                    like.incrementAndGet();
                }
            });
        });
        statisticResponse.setDislikesCount(dislike.get());
        statisticResponse.setLikesCount(like.get());
        statisticResponse.setPostsCount(posts.size());
        statisticResponse.setViewsCount(postRepository.findPostsViewCountByUser(user1.getId()));
        statisticResponse.setFirstPublication(postRepository.getDateFromUser(user1.getId()).getTime()/1000);
        return statisticResponse;
    }

    @Override
    public StatisticResponse getStatisticAll(){
        try {
            List<Post> posts = postRepository.findAll();
            StatisticResponse statisticResponse = new StatisticResponse();
            AtomicInteger dislike = new AtomicInteger(0);
            AtomicInteger like = new AtomicInteger(0);
            AtomicReference<Integer> count = new AtomicReference<>(0);
            List<Date> dates = new ArrayList<>();
            posts.forEach(post -> {
                count.set(count.get() + post.getViewCount());
            });
            posts.forEach(post -> {
                dates.add(post.getTimePost());
            });
            posts.forEach(post -> {
                post.getVotes().forEach( postVotes -> {
                    if (postVotes.getValue() == -1){
                        dislike.incrementAndGet();
                    }
                    else {
                        like.incrementAndGet();
                    }
                });
            });
            Collections.sort(dates);
            statisticResponse.setDislikesCount(dislike.get());
            statisticResponse.setLikesCount(like.get());
            statisticResponse.setPostsCount(posts.size());
            statisticResponse.setViewsCount(count.get());
            statisticResponse.setFirstPublication(dates.get(0).getTime()/1000);
            return statisticResponse;
        } catch (Exception ex){
            ex.printStackTrace();
            return new StatisticResponse();
        }
    }
}
