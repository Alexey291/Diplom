package main.api.response;

import lombok.Getter;
import lombok.Setter;
import main.api.response.marker.Response;

@Getter
@Setter
public class NewPostResponce implements Response {

    private long timestamp;
    private byte active;
    private String title;
    private String text;
    private String[] tags;
}
