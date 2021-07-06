package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import main.api.response.marker.Response;
@Getter
public class ResultResponse implements Response {
    private final boolean result;
    public static final ResultResponse TRUE = new ResultResponse(true);
    public static final ResultResponse FALSE = new ResultResponse(false);

    public ResultResponse(boolean result) {
        this.result = result;
    }

    public String getResult(){
        if (!result){
            return "{\"result\": false}";
        }
        else {
            return "{\"result\": true}";
        }
    }

}
