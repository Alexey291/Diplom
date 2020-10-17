package main.controller;

import main.Entity.Post;
import main.api.response.PostResponse;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public ResponseEntity getPostId(@PathVariable int id){
        return ResponseEntity.ok(postService.getOnePost(id));
    }
}
