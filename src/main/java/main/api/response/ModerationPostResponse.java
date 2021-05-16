package main.api.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ModerationPostResponse {
    private int post_id;
    private String decision;
}
