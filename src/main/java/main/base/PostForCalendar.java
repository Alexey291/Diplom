package main.base;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.w3c.dom.UserDataHandler;

public class PostForCalendar {
    int count;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
