package main.api.response;

import main.base.Tag;

import java.util.List;

public class TagResponse {
    private List <Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
