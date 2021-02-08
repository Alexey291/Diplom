package main.dao;

import main.base.CommentForPost;

import java.sql.SQLException;

public interface PostCommentDAO {
    public int save(CommentForPost requestComment, String email) throws SQLException;
}
