package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.entity.User;

@Data
public class LoginResponse {
    private boolean result;
    @JsonProperty("user")
    private UserLoginResponse userLoginResponse;

    public void setUserLoginResponse(UserLoginResponse userLoginResponse) {
        this.userLoginResponse = userLoginResponse;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


}
