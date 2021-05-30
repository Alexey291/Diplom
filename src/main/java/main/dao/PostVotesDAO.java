package main.dao;

import main.entity.PostVotes;
import main.entity.User;

import java.sql.SQLException;

public interface PostVotesDAO {
    public Boolean save(String postVotesId, String email, User user) throws SQLException;
    public Boolean saveDislike(String postVotesId, String email, User user) throws SQLException;
    public void delete(String postVotes, String email);
}
