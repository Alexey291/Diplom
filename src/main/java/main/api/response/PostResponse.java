package main.api.response;

import main.api.response.marker.Response;
import main.base.PostListResponse;

import java.util.ArrayList;
import java.util.List;

public class PostResponse implements Response {
    private int count;
    private List<PostListResponse> posts = new ArrayList<>();

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
