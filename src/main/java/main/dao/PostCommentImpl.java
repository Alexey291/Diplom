package main.dao;

import main.base.CommentForPost;
import main.entity.Post;
import main.entity.PostComment;
import main.repo.PostCommentRepository;
import main.repo.PostRepository;
import main.repo.UserRepository;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostCommentImpl implements PostCommentDAO{
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;
    private final String INSERT_QUERY = "insert into posts_comments (post_id, votes_id) values (?,?);";
    @Autowired
    public PostCommentImpl(PostRepository postRepository, PostCommentRepository postCommentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public int save(CommentForPost requestComment, String email) throws SQLException {
        PostComment postComment1 = new PostComment();
        Post post = postRepository.findById(requestComment.getPost_id());
        postComment1.setPost(post);
        postComment1.setPost_id(postRepository.findById(requestComment.getPost_id()));
        postComment1.setText(requestComment.getText());
        postComment1.setTime(new Date());
        postComment1.setParent_id(requestComment.getParent_id());
        postComment1.setUser(userRepository.findByEmail(email).get());
        postComment1.setUser_id(userRepository.findByEmail(email).get());
        List<PostComment> list = post.getComments();
        list.add(postComment1);
        post.setComments(list);
        postRepository.save(post);
        //postCommentRepository.save(postComment1);

        try {
            return postComment1.getId();
        } catch (Exception exception){
            exception.printStackTrace();
            return 0;

        }


    }
}
