package main.Entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 1 AND p.`time_post` < NOW()",
            countQuery = "SELECT count(*) FROM Posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 1",
            nativeQuery = true)
    Page<Post> getPostsWithPagination(Pageable pageable);
    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 1 AND p.`time_post` < NOW() ORDER BY 'view_count' DESC",
            countQuery = "SELECT count(*) FROM Posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 1",
            nativeQuery = true)
    Page<Post> getPostsPopWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 1 AND p.`time_post` < NOW() ORDER BY 'time_post' DESC",
            countQuery = "SELECT count(*) FROM Posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 1",
            nativeQuery = true)
    Page<Post> getPostsDateWithPagination(Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Post findById(int id);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 1 AND p.`time_post` < NOW();",
    nativeQuery = true)
    List<Post> getRecentPost();


    List<Post> findByTimePost(Date date);
    //int countAllByIsActiveAndModerationStatus(int isActive, int moderationStatus);
}
