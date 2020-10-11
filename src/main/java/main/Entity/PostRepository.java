package main.Entity;

import org.hibernate.annotations.SortComparator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query(value = "SELECT * FROM posts p WHERE p.code = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW(); ",
            nativeQuery = true)
    List<Post> getRecentPosts();
}
