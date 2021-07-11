package main.dao;

import main.api.response.ProfileResponse;
import main.api.response.ResultResponse;
import main.entity.User;
import main.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ModerationProfileDAOImpl implements ModerationProfileDAO{
    private final UserRepository userRepository;
    @Autowired
    public ModerationProfileDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResultResponse postStatus(ProfileResponse profileResponse) {
        User user = userRepository.findByEmail(profileResponse.email).get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setPassword((passwordEncoder.encode(profileResponse.getPassword())));
        userRepository.save(user);
        return ResultResponse.TRUE;
    }
}
