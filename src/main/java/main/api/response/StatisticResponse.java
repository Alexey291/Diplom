package main.api.response;

import lombok.Getter;
import lombok.Setter;
import main.api.response.marker.Response;

@Getter
@Setter
public class StatisticResponse implements Response {
    private int postsCount;
    private int likesCount;
    private int dislikesCount;
    private int viewsCount;
    private long firstPublication;
}
