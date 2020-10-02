package main.api.response;

import main.base.Post;

public class PostResponse {
    private int count;
    private Post post;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
