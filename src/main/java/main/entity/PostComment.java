package main.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "post_comments")
public class PostComment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
   // private int parent_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id",  insertable = false, updatable = false)
    private Post post;
    private int post_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    private User user;
    private int user_id;
    private Date time;
    private String text;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

  //  public int getParent_id() {
  //      return parent_id;
  //  }

   // public void setParent_id(int parent_id) {
    //    this.parent_id = parent_id;
   // }
    @JsonGetter(value = "timestamp")
    public long getTimeForFront(){
        return time.getTime()/1000;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(@NonNull Post post) {
        this.post_id = post.getId();
    }

    public User getUser() {
        return user;
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

    public Date getTime() {
        return time;
    }

    public void setTime(@NonNull Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }


}
