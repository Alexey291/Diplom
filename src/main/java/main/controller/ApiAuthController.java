package main.controller;

import main.api.response.InitResponse;
import main.api.response.UserResponse;
import main.entity.User;
import main.entity.UserRepository;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    @Autowired
    CaptchaService captchaService;
    @Autowired
    UserRepository userRepository;

    public ApiAuthController(CaptchaService captchaService){
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha")
    private ResponseEntity getCaptcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCode());
    }
    @PostMapping("/register")
    private void putUser(@RequestBody User user){
        User user1 = new User();
        user1.setCode(user.getCode());
        user1.setEmail(user.getEmail());
        user1.setIs_moderator(false);
        user1.setName(user.getName());
        user1.setReg_time(new Date());
        user1.setPassword(user.getPassword());
        userRepository.save(user1);
    }
}
