package main.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 1 AND p.`time_post` < NOW()",
            countQuery = "SELECT count(*) FROM Posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 1",
            nativeQuery = true)
    Page<Post> getPostsWithPagination(Pageable pageable);


    @Query(value = "SELECT * " +
            "FROM posts p2 " +
            "WHERE p2.is_active = 1 AND p2.moderation_status = 1 " +
            "ORDER BY (SELECT COUNT(*) FROM post_comments pc WHERE pc.post_id = p2.id) DESC",
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

    @Query(value = "SELECT * " +
            "FROM posts p2 " +
            "WHERE p2.is_active = 1 AND p2.moderation_status = 1 " +
            "ORDER BY (SELECT COUNT(*) FROM post_votes pv WHERE pv.post_id = p2.id AND pv.value = 1) DESC",
            countQuery = "SELECT count(*) FROM Posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 1",
            nativeQuery = true)
    Page<Post> getPostsBestWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = :isActive AND p.moderation_status = :moderation AND p.`time_post` > :dayBefore " +
            "AND p.`time_post` < :dayAfter",
            nativeQuery = true)
    Page<Post> getPostDateWithPagination(@Param("isActive")int isActive,
                           @Param("moderation") int moderationStatus,
                           @Param("dayBefore") Date dayBefore,
                           @Param("dayAfter") Date dayAfter, Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM posts p2 " +
            "WHERE p2.is_active = 1 AND p2.moderation_status = 1 AND p2.text LIKE CONCAT ('%',:query,'%')",
          nativeQuery = true)
    Page<Post> findPostsWithPartOfTextWithPagination(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = :isActive AND p.moderation_status = :moderation " +
            "AND p.id = (SELECT post_id FROM tag2post WHERE tag_id = (SELECT id FROM tags WHERE name = :name))",
            nativeQuery = true)
    Page<Post> getPostTagWithPagination(@Param("isActive")int isActive,
                                         @Param("moderation") int moderationStatus,
                                         @Param("name") String name,
                                         Pageable pageable);



}
