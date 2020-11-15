package main.controller;

import main.api.response.InitResponse;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    @Autowired
    CaptchaService captchaService;

    public ApiAuthController(CaptchaService captchaService){
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha")
    private ResponseEntity getCaptcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCode());
    }
}
