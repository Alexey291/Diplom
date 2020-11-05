package main.api.response;

import main.base.PostListResponse;

import java.util.List;

public class PostResponse {
    private int count;
    private List<PostListResponse> posts;

    public List<PostListResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<PostListResponse> posts) {
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
