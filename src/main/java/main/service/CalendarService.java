package main.service;

import main.entity.Post;
import main.entity.PostRepository;
import main.api.response.CalendarResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class CalendarService {
    @Autowired
    PostRepository postRepository;

    public CalendarResponse getCalendar1(int year){
        List<Post> posts = postRepository.getRecentPost();
        List<LocalDate> date = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate date1 = LocalDate.of(year,1,1);
        date1.format(dateFormat);
        LocalDate date2 = LocalDate.of(year,12,12);
        while (date1.isBefore(date2)){
            date.add(date1);
            date1 = date1.plusDays(1);
        }
        HashMap<String, Integer> datePost1 = new HashMap<>();
        List <String> list1 = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < date.size(); i++){
            for(Post post : posts ) {
                if(date.get(i).isEqual(post.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
                    count++;
                }
            }
            if(count != 0){
            datePost1.put(date.get(i).toString(),count);}

            count = 0;
        }
        CalendarResponse calendarResponse = new CalendarResponse();

        list1.add(Integer.toString(year));
        list1.add(Integer.toString(year - 1));
        list1.add(Integer.toString(year - 2));
        list1.add(Integer.toString(year - 3));
        calendarResponse.setPosts(datePost1);
        calendarResponse.setYears(list1);

        return calendarResponse;
    }
}
