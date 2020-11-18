package main.controller;

import main.api.response.LoginRequest;
import main.api.response.LoginResponse;
//import main.entity.User;
import main.api.response.UserLoginResponse;
import main.api.response.UserResponse;
import main.entity.UserRepository;
import main.security.SecurityUser;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    CaptchaService captchaService;
    @Autowired
    UserRepository userRepository;

    public ApiAuthController(AuthenticationManager authenticationManager, UserRepository userRepository, CaptchaService captchaService){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha")
    private ResponseEntity getCaptcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCode());
    }
    @PostMapping("/register")
    private void putUser(@RequestBody main.entity.User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setReg_time(new Date());
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        userRepository.save(user);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Authentication auth =authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        main.entity.User currentUser = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(user.getUsername()));
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setModeration(currentUser.getIs_moderator());
        userResponse.setId(currentUser.getId());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);
        return ResponseEntity.ok(loginResponse);
    }
}
