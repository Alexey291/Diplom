package main.api.response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NewPostResponce {

    private long timestamp;
    private byte active;
    private String title;
    private String text;
    private String[] tags;
}
