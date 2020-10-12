package main.service;

import main.Entity.Tags;
import main.Entity.TagsRepository;
import main.api.response.TagResponse;
import main.base.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    TagsRepository tagsRepository;
    public TagResponse getTags(){
        TagResponse tagResponse = new TagResponse();
        List<Tags> tags1 = tagsRepository.getRecentTags();
        tagResponse.setTags(tags1);
        return tagResponse;
    }
}
