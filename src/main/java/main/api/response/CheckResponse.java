package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.api.response.marker.Response;

public class CheckResponse implements Response {
    private boolean result;
    @JsonProperty("user")
    private UserResponse userResponse;


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
