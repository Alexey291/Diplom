package main.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ModerationPostResponse {
    private int count;
    private List<PostResponse> postResponseList;
}
