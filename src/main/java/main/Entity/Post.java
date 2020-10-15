package main.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import main.Status;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
@Data
@Entity
@Table(name = "Posts")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Boolean is_active;
    private Status moderation_status;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    private User user;
    @JsonProperty("timestamp")
    private Date time;
    @JsonProperty("announce")
    private String text;
    @JsonProperty("viewCount")
    private int view_count;
    private int user_id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostVotes> votes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


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

    public void setUser(User user) {
        this.user = user;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(@NonNull User user) {
        this.user_id = user.getId();
    }
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(@NonNull Boolean is_active) {
        this.is_active = is_active;
    }

    public Status getModeration_status() {
        return moderation_status;
    }

    public void setModeration_status(@NonNull Status moderation_status) {
        this.moderation_status = moderation_status;
    }

    public User getUser() {
        return user;
    }

    public Long getTime() {
        return time.getTime();
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }



}
