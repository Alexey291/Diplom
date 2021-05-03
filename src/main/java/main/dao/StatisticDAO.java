package main.dao;

import main.api.response.StatisticResponse;
import main.entity.User;
import main.repo.PostRepository;

public interface StatisticDAO {
    public StatisticResponse getStatisticUser(User user);
}
