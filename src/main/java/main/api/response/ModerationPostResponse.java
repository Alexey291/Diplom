package main.api.response;

import lombok.Getter;
import lombok.Setter;
import main.api.response.marker.Response;


@Getter
@Setter
public class ModerationPostResponse implements Response {
    private int post_id;
    private String decision;
}
