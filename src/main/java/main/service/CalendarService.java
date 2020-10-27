package main.service;

import main.Entity.Post;
import main.Entity.PostRepository;
import main.api.response.CalendarResponse;
import main.base.PostForCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class CalendarService {
    @Autowired
    PostRepository postRepository;
/*
    public CalendarResponse getCalendar(int year){
        List<Post> posts = postRepository.getRecentPost();
        HashMap<Integer, LocalDate> date = new HashMap<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate date1 = LocalDate.of(year,1,1);
        date1.format(dateFormat);
        LocalDate date2 = LocalDate.of(year,12,12);
        int i = 0;
        while (date1.isBefore(date2)){
            date.put(i,date1);
            i++;
            date1 = date1.plusDays(1);
        }
        int value = 0;
        Map.Entry<HashMap<Integer, LocalDate>, Integer> datePost = Map.entry(date,value);
        HashMap<LocalDate, Integer> datePost1 = new HashMap<>();
        for (Post post : posts){
          //  for (Map.Entry<Integer, LocalDate> entry : date.entrySet()){
               // if(entry.getValue().isEqual(post.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
              //  }
          //  }
            for (LocalDate z : date.values()){
                if (z.isEqual(post.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
                    value++;
                }
            }
            datePost1.put(post.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),value);
            value = 0;
        }
        CalendarResponse calendarResponse = new CalendarResponse();
        List<PostForCalendar> list = new ArrayList<>();
        List <Integer> dfs = (List<Integer>) datePost1.values();
        for (int j = 0; j < dfs.size();j++){
            PostForCalendar postForCalendar = new PostForCalendar();
            postForCalendar.setCount(dfs.get(j));
            list.add(postForCalendar);
        }
        for (PostForCalendar d : list){
            System.out.println(d.getCount());
        }
       // calendarResponse.setPosts(list);
        //calendarResponse.setYears(new int[]{year});
        return new CalendarResponse();
    }

 */
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
//date.containsValue(post.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())