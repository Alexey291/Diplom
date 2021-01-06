package main.controller;

import main.api.response.*;
import main.entity.PostComment;
import main.entity.PostCommentRepository;
import main.entity.PostRepository;
import main.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final CheckService checkService;
    private final TagService tagService;
    private final PostService postService;
    private final CalendarService calendarService;
    @Autowired
    private final PostCommentRepository postCommentRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, CheckService checkService, TagService tagService, PostService postService, CalendarService calendarService, PostCommentRepository postCommentRepository, PostRepository postRepository) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.checkService = checkService;
        this.tagService = tagService;
        this.postService = postService;
        this.calendarService = calendarService;
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
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

    @GetMapping("/api/calendar")
    private ResponseEntity calendar(@RequestParam(required = false,defaultValue = "2020") int year){
        return ResponseEntity.ok(calendarService.getCalendar1(year));
    }
    @PostMapping("/api/comment")
    private void comment(@RequestBody PostComment postComment){
        if (postRepository.findById(postComment.getPost_id()) == null){
            ResponseEntity.badRequest().build();
        }
        else {
            System.out.println(postComment.getPost_id() + " vot");
            postCommentRepository.save(postComment);

        }
    }
}
