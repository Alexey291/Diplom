package main.service;

import main.api.response.TagResponse;
import main.base.Tag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    public TagResponse getTags(){
        TagResponse tagResponse = new TagResponse();
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        Tag tag1 = new Tag();
        tag.setName("Java");
        tag.setWeight(1);
        tag1.setName("PHP");
        tag1.setWeight(0.5);
        tags.add(tag);
        tags.add(tag1);
        tagResponse.setTags(tags);
        return tagResponse;
    }
}
