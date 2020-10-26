package main.Entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import main.Status;

import main.base.PostCommentsResponce;
import main.base.UserPostResponse;
import org.apache.tomcat.jni.Local;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Column(name = "time_post")
    private Date timePost;
    private String text;
    @JsonProperty("viewCount")
    private int view_count;
    private String title;
    private int user_id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostVotes> votes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    //листы comments и votes почему-то приходят пустыми, хотя связи должны работать а данные заполнены через миграцию.
    //Поэтому я поставил начальные значения 10, 1 и 4, что-бы показать что ответы на запросы приходят.





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
    // Время тут сильно преувеличино, не могу понять почему
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

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

}
