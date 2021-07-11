package main.dao;

import main.api.response.ProfileResponse;
import main.api.response.ResultResponse;

public interface ModerationProfileDAO {
    public ResultResponse postStatus(ProfileResponse profileResponse);
}
