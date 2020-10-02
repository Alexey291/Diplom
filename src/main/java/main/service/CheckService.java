package main.service;

import main.api.response.CheckResponse;
import main.api.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class CheckService {
    public CheckResponse getCheck(){
        CheckResponse checkResponse = new CheckResponse();
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("leshak549@gmail.com");
        userResponse.setId(58);
        userResponse.setModeration(true);
        userResponse.setModerationCount(112);
        userResponse.setName("Иван Петров");
        userResponse.setPhoto("C:\\test1\\image0");
        userResponse.setSettings(true);
        checkResponse.setResult(false);
        checkResponse.setUserResponse(userResponse);
        return checkResponse;
    }
}
