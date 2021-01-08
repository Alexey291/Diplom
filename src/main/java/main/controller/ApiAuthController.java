package main.controller;

import main.api.response.LoginRequest;
import main.api.response.LoginResponse;
//import main.entity.User;
import main.api.response.UserLoginResponse;
import main.entity.Post;
import main.entity.PostRepository;
import main.entity.Status;
import main.entity.UserRepository;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
//@RequestMapping("/api/auth")
public class ApiAuthController {

        private final AuthenticationManager authenticationManager;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CaptchaService captchaService;
    @Autowired
    private final UserRepository userRepository;
    private String email;

    public ApiAuthController(AuthenticationManager authenticationManager, PostRepository postRepository, UserRepository userRepository, CaptchaService captchaService){
        this.authenticationManager = authenticationManager;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.captchaService = captchaService;
    }

    @GetMapping("/api/auth/captcha")
    private ResponseEntity getCaptcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCode());
    }
    @PostMapping("/api/auth/register")
    private void putUser(@RequestBody main.entity.User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setReg_time(new Date());
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        user.setIs_moderator(false);
        userRepository.save(user);
    }
    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        email = loginRequest.getEmail();
        System.out.println(loginRequest.getEmail());
        System.out.println(loginRequest.getPassword());
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        System.out.println("1");
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        main.entity.User currentUser = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(user.getUsername()));
        System.out.println(currentUser.getEmail() + "______");
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setModeration(currentUser.getIs_moderator());
        userResponse.setId(currentUser.getId());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);
        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping("/api/post")
    private void post(@RequestBody Post post){
       // post.setModerationStatus(Status.NEW);
        main.entity.User user = userRepository.findByEmail(email).get();
       // post.setUserId(user.getId());
        Post postFinal = new Post();
        postFinal.setModerationStatus(Status.ACCEPTED);
        postFinal.setUserId(user.getId());
        postFinal.setIsActive(true);
        postFinal.setUser(user);
        postFinal.setText(post.getText());
        postFinal.setTitle(post.getTitle());
        postFinal.setTime(new Date());

        postRepository.save(postFinal);
    }
}
