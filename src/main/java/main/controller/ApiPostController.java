package main.controller;

import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {


    @Autowired
    private final PostService postService;

    public ApiPostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("")
   // @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity getPost(@RequestParam(required = false,defaultValue = "0") int offset,
                                  @RequestParam(required = false,defaultValue = "10") int limit,
                                  @RequestParam (required = false, defaultValue = "recent") String mode){
        return ResponseEntity.ok(postService.getPosts(offset,limit,mode));
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity getPostId(@PathVariable int id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    @GetMapping("/search")
    public ResponseEntity getPostQuery(@RequestParam(required = false,defaultValue = "0") int offset,
                                       @RequestParam(required = false, defaultValue = "10") int limit,
                                       @RequestParam(required = false,defaultValue = "0")String query){
        return ResponseEntity.ok(postService.getPostQuery(offset,limit,query));
    }
    @GetMapping("/byDate")
    public ResponseEntity getPostByDate(@RequestParam(required = false,defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int limit,
                                        @RequestParam (required = false, defaultValue = "2020-01-01") String date){
        LocalDate date1 = LocalDate.parse(date);
        return ResponseEntity.ok(postService.getPostByDate(offset,limit,date1));
    }
    @GetMapping("/byTag")
    public ResponseEntity getPostByTag(@RequestParam(required = false,defaultValue = "0") int offset,
                                       @RequestParam(required = false, defaultValue = "10") int limit,
                                       @RequestParam (required = false, defaultValue = "java") String tag){
        return ResponseEntity.ok(postService.getPostByTag(offset,limit,tag));}
}
