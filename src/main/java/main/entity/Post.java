package main.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import main.base.PostCommentsResponce;
import main.base.UserPostResponse;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "Posts")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name="moderation_status", columnDefinition="enum('NEW','ACCEPTED','DECLINED')")
    @Enumerated(EnumType.STRING)
    private Status moderationStatus;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    private User user;
    @Column(name = "time_post")
    private Date timePost;
    private String text;
    @Column(name = "view_count")
    private int viewCount;
    private String title;
    @Column(name = "user_id")
    private int userId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostVotes> votes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();


    public List<PostCommentsResponce> getCommentsResponce(){
        List<PostCommentsResponce> newComments = new ArrayList<>();
        for (PostComment comment : comments){
            PostCommentsResponce postCommentsResponce = new PostCommentsResponce();
            postCommentsResponce.setId(comment.getId());
            postCommentsResponce.setText(comment.getText());
            postCommentsResponce.setTimestamp(comment.getTimeForFront());
            postCommentsResponce.setUser(new UserPostResponse(comment.getUser().getId(), comment.getUser().getName()));
            newComments.add(postCommentsResponce);
        }
        return newComments;
    }


    public int getCommentsCount() {
        int countComment = 0;
        for (PostComment comment : comments) {
            countComment++;
        }
        return countComment;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    public int getUserId() {
        return userId;
    }
    public void setUserId(@NonNull User user) {
        this.userId = user.getId();
    }
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(@NonNull Boolean is_active) {
        this.isActive = is_active;
    }

    public Status getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(@NonNull Status moderation_status) {
        this.moderationStatus = moderation_status;
    }

    public User getUser() {
        return user;
    }

    public Long getTimeForFront() {
        return timePost.getTime()/1000;
    }


    public Date getTime() {
        return timePost;
    }

    public void setTime(Date timePost) {
        this.timePost = timePost;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount){
        this.viewCount = viewCount;
    }

}
