package main.api.response;

import main.base.PostForCalendar;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class CalendarResponse {
    private List<String> years;
    private HashMap<String,Integer> posts;


    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public HashMap<String,Integer> getPosts() {
        return posts;
    }

    public void setPosts(HashMap<String,Integer> posts) {
        this.posts = posts;
    }
}
