package main.controller;

import main.api.response.*;
import main.service.CheckService;
import main.service.PostService;
import main.service.SettingsService;
import main.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final CheckService checkService;
    private final TagService tagService;
    private final PostService postService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, CheckService checkService, TagService tagService, PostService postService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.checkService = checkService;
        this.tagService = tagService;
        this.postService = postService;
    }
    @GetMapping("/api/settings")
    private SettingsResponse setting(){
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/api/init")
    private InitResponse init(){
        return initResponse;
    }

    @GetMapping("/api/auth/check")
    private CheckResponse check(){
        return checkService.getCheck();
    }

    @GetMapping("/api/tag")
    private TagResponse tags(){
        return tagService.getTags();
    }

    @GetMapping("/api/post")
    private PostResponse posts(){
        return postService.getPosts();
    }
}
