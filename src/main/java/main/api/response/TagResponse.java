package main.api.response;

import main.api.response.marker.Response;
import main.entity.Tags;


import java.util.List;

public class TagResponse implements Response {
    private List <Tags> tags;

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
}
