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
import java.util.Date;

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
        postComment1.setPost(postRepository.findById(requestComment.getPost_id()));
        postComment1.setPost_id(postRepository.findById(requestComment.getPost_id()));
        postComment1.setText(requestComment.getText());
        postComment1.setTime(new Date());
        postComment1.setParent_id(requestComment.getParent_id());
        postComment1.setUser(userRepository.findByEmail(email).get());
        postComment1.setUser_id(userRepository.findByEmail(email).get());
        postCommentRepository.save(postComment1);
        //jdbcTemplate.update(INSERT_QUERY,post.getId(),postComment1.getId());
        try {
            return postComment1.getId();
        } catch (Exception exception){
            exception.printStackTrace();
            return 0;

        }


    }
}
