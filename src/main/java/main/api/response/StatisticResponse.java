package main.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticResponse {
    private int postsCount;
    private int likesCount;
    private int dislikesCount;
    private int viewsCount;
    private long firstPublication;
}
