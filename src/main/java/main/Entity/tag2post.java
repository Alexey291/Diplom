package main.Entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.swing.text.html.HTML;

@Entity
@Table(name =  "tag2post")
public class tag2post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Post post;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Tags tag;
    private int tag_id;
    private int post_id;

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(@NonNull Tags tag) {
        this.tag_id = tag.getId();
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(@NonNull Post post) {
        this.post_id = post.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(@NonNull Post post) {
        this.post = post;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(@NonNull Tags tag) {
        this.tag = tag;
    }


}
