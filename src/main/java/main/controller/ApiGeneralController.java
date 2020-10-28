package main.controller;

import main.api.response.*;
import main.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController

public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final CheckService checkService;
    private final TagService tagService;
    private final PostService postService;
    private final CalendarService calendarService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, CheckService checkService, TagService tagService, PostService postService, CalendarService calendarService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.checkService = checkService;
        this.tagService = tagService;
        this.postService = postService;
        this.calendarService = calendarService;
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

}
