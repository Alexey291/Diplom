package main.service;

import main.entity.User;
import main.repo.UserRepository;
import main.api.response.CheckResponse;
import main.api.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckService {
    @Autowired
    UserRepository userRepository;
    public CheckResponse getCheck(){
        CheckResponse checkResponse = new CheckResponse();
        UserResponse userResponse = new UserResponse();
       try {
           User user = userRepository.getUser();
           userResponse.setEmail(user.getEmail());
           userResponse.setId(user.getId());
           userResponse.setModeration(user.getIs_moderator());
         //  userResponse.setModerationCount(112);
           userResponse.setName(user.getName());
           //userResponse.setPhoto("C:\\test1\\image0");
           //userResponse.setSettings(true);
           checkResponse.setResult(false);
           checkResponse.setUserResponse(userResponse);
           return checkResponse;
       }catch (Exception ex){
           ex.printStackTrace();
       }
       return new CheckResponse();
    }
}
 /*userResponse.setEmail("leshak549@gmail.com");
         userResponse.setId(58);
         userResponse.setModeration(true);
         userResponse.setModerationCount(112);
         userResponse.setName("Иван Петров");
         userResponse.setPhoto("C:\\test1\\image0");
         userResponse.setSettings(true);
         checkResponse.setResult(false);

  */