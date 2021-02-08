package main.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.base.Role;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Boolean is_moderator;
    @JsonProperty("reg_time")
    private Date regTime;
    private String name;
    @JsonProperty("e_mail")
    private String email;
    private String password;
    private char[] code;
    private String photo;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostVotes> votes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

    public List<PostVotes> getVotes() {
        return votes;
    }

    public void setVotes(List<PostVotes> votes) {
        this.votes = votes;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Boolean getIs_moderator() {
        return is_moderator;
    }

    public void setIs_moderator(@NonNull Boolean is_moderator) {
        this.is_moderator = is_moderator;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(@NonNull Date regTime) {
        this.regTime = regTime;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public char[] getCode() {
        return code;
    }

    public void setCode(char[] code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Role getStatus(){
        return is_moderator ? Role.MODERATOR : Role.USER;
    }



}
