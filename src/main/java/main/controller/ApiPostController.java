package main.controller;

import main.api.response.PostResponse;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {
    public ApiPostController(PostService postService){
        this.postService = postService;
    }
    @Autowired
    private final PostService postService;
    @GetMapping("")
    public ResponseEntity getPost(@RequestParam(required = false,defaultValue = "0") int offset, @RequestParam(required = false,
    defaultValue = "10") int limit, @RequestParam (required = false, defaultValue = "recent") String mode){
        return ResponseEntity.ok(postService.getPosts(offset,limit,mode));
    }
}
