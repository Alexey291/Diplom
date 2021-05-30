package main.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    @JsonProperty("e_mail")
    private String email;
    private String name;
    private String password;
    private String captcha;
    private String captcha_secret;
}
