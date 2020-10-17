package main.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 1 AND p.`time_post` < NOW(); ",
            nativeQuery = true)
    List<Post> getRecentPosts();
    Post findById(int id);
}
    //SELECT COUNT(*) FROM Post p WHERE p.isActive = 1 AND p.moderationStatus = 1 AND p.time <= NOW();

      //  SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 1 AND p.`time` < NOW();