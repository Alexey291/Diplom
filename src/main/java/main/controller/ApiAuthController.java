package main.controller;

import main.api.response.*;
//import main.entity.User;
import main.base.CommentForPost;
import main.base.PostListResponse;
import main.dao.PostCommentImpl;
import main.dao.PostVotesDAOIml;
import main.dao.StatisticDAOImpl;
import main.entity.*;
import main.repo.PostCommentRepository;
import main.repo.PostRepository;
import main.repo.PostVotesRepository;
import main.repo.UserRepository;
import main.request.UserRegisterRequest;
import main.service.CaptchaService;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;


@RestController
//@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthenticationManager authenticationManager;

    private final PostRepository postRepository;

    private final CaptchaService captchaService;

    private final UserRepository userRepository;
    private String email;

    private final PostService postService;

    private final PostVotesRepository postVotesRepository;

    private final PostCommentRepository postCommentRepository;
    @Autowired
    public ApiAuthController(AuthenticationManager authenticationManager, PostRepository postRepository, UserRepository userRepository, CaptchaService captchaService, PostService postService, PostVotesRepository postVotesRepository, PostCommentRepository postCommentRepository){
        this.authenticationManager = authenticationManager;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.captchaService = captchaService;
        this.postService = postService;
        this.postVotesRepository = postVotesRepository;
        this.postCommentRepository = postCommentRepository;

    }

    @GetMapping("/api/auth/captcha")
    private ResponseEntity getCaptcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCode());
    }
    @PostMapping("/api/auth/register")
    private String putUser(@RequestBody UserRegisterRequest user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        main.entity.User user1 = new main.entity.User();

        System.out.println(userRepository.findByEmail(user.getEmail()).isPresent());
        if (userRepository.findByEmail(user.getEmail()).isPresent() || !user.getCaptcha().equals(user.getCaptcha_secret())){
            return "{\n" +
                    " \"result\": false,\n" +
                    " \"errors\": {\n" +
                    " \"email\": \"Этот e-mail уже зарегистрирован\",\n" +
                    " \"name\": \"Имя указано неверно\",\n" +
                    " \"password\": \"Пароль короче 6-ти символов\",\n" +
                    " \"captcha\": \"Код с картинки введён неверно\"\n" +
                    " }\n" +
                    "}\n";
        }else {
        user1.setPassword((passwordEncoder.encode(user.getPassword())));
        user1.setIs_moderator(false);
        user1.setRegTime(new Date());
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        userRepository.save(user1);
        return "{\"result\": true}";}
    }
    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        email = loginRequest.getEmail();
        System.out.println(loginRequest.getEmail());
        System.out.println(loginRequest.getPassword());
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        System.out.println("1");
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        main.entity.User currentUser = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(user.getUsername()));
        System.out.println(currentUser.getEmail() + "______");
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setModeration(currentUser.getIs_moderator());
        userResponse.setId(currentUser.getId());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);
        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping("/api/post")
    private String post(@RequestBody NewPostResponce post){
       // post.setModerationStatus(Status.NEW);
        main.entity.User user = userRepository.findByEmail(email).get();
       // post.setUserId(user.getId());
        Post post1 = new Post();
        post1.setTime(new Date());
        post1.setText(post.getText());
        post1.setTitle(post.getTitle());
        post1.setUser(user);
        post1.setIsActive(true);
        post1.setUserId(user.getId());
        post1.setModerationStatus(Status.ACCEPTED);
        postRepository.save(post1);
        return new String("{\"result\": true}");
    }


    @PutMapping("/api/post/{id}")
    private String postPut(@RequestBody NewPostResponce post,
                           @PathVariable int id){
        postRepository.delete(postRepository.findById(id));
        main.entity.User user = userRepository.findByEmail(email).get();
        Post post1 = new Post();
        post1.setTime(new Date());
        post1.setText(post.getText());
        post1.setTitle(post.getTitle());
        post1.setUser(user);
        post1.setIsActive(true);
        post1.setUserId(user.getId());
        post1.setModerationStatus(Status.ACCEPTED);
        postRepository.save(post1);
        return new String("{\"result\": true}");
    }
    @GetMapping("/api/statistics/my")
    private StatisticResponse getStatistic(){
        main.entity.User user = userRepository.findByEmail(email).get();
        StatisticDAOImpl statisticDAO = new StatisticDAOImpl(postRepository,userRepository);
        StatisticResponse statisticResponse = statisticDAO.getStatisticUser(user);
        return statisticResponse;
    }

    @PostMapping("api/post/like")
    private String postLike(@RequestBody String postId)
    {
        try {
            main.entity.User user = userRepository.findByEmail(email).get();
            PostVotesDAOIml postVotesDAOIml = new PostVotesDAOIml(userRepository,postVotesRepository,postRepository);
            boolean result = postVotesDAOIml.save(postId,email,user);
            if (result){
            return ("{\"result\": true}");}
            else {
                return ("{\"result\": false}");
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }

        return new String("{\"result\": true}");
    }
    @PostMapping("api/post/dislike")
    private String postDisLike(@RequestBody String postId)
    {
        try {
            main.entity.User user = userRepository.findByEmail(email).get();
            PostVotesDAOIml postVotesDAOIml = new PostVotesDAOIml(userRepository,postVotesRepository,postRepository);
            boolean result = postVotesDAOIml.saveDislike(postId,email,user);
            if (result){
                return ("{\"result\": true}");}
            else {
                return ("{\"result\": false}");
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return new String("{\"result\": true}");
    }
    @GetMapping("api/post/my")
    public ResponseEntity getMyPost(@RequestParam(required = false, defaultValue = "0") int offset,
                                  @RequestParam(required = false, defaultValue = "10") int limit,
                                  @RequestParam(required = false, defaultValue = "inactive") String status){
        main.entity.User user = userRepository.findByEmail(email).get();
        return ResponseEntity.ok(postService.getMyPosts(offset,limit,status,user.getId()));
    }
    @PostMapping("/api/comment")
    private String comment(@RequestBody CommentForPost postComment){
        try {
            PostCommentImpl postCommentImpl = new PostCommentImpl(postRepository,postCommentRepository,userRepository);
            int id =  postCommentImpl.save(postComment,email);
            return new String("{\"id\": " + id + "}");
        } catch (Exception e){
            e.printStackTrace();
            return new String("{\"result\": " + "false" + "}");
        }
    }
    @GetMapping("/api/post/moderation")
    private ResponseEntity<PostResponse> getModeration(@RequestParam(required = false,defaultValue = "0") int offset,
                                                       @RequestParam(required = false,defaultValue = "10") int limit,
                                                       @RequestParam (required = false, defaultValue = "new") String status){
        try {
            System.out.println(status);
            return ResponseEntity.ok(postService.getPostsByModeration(offset,limit,status));
        } catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    @PostMapping("/api/moderation")
    private ResponseEntity<String> postModeration(@RequestBody ModerationPostResponse moderationPostResponse){
        try {
            return ResponseEntity.ok(postService.postModerationStatus(moderationPostResponse));
        } catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }
}
