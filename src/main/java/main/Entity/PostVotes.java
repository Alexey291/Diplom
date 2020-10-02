package main.Entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_votes")
public class PostVotes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id",  insertable = false, updatable = false)
    private Post post;
    private Date time;
    private byte value;
    private int user_id;
    private int post_id;

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(@NonNull Post post) {
        this.post = post;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(@NonNull Date time) {
        this.time = time;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(@NonNull User user) {
        this.user_id = user.getId();
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(@NonNull Post post) {
        this.post_id = post.getId();
    }




}
