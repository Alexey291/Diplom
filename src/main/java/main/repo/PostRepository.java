package main.repo;

import main.entity.Post;
import main.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time_post` < NOW()",
            countQuery = "SELECT count(*) FROM posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 'ACCEPTED'",
            nativeQuery = true)
    Page<Post> getPostsWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 0 AND p.user_id = :id",
    nativeQuery = true)
    Page<Post> getPostsWithPaginationForAuthUser(Pageable pageable,
                                                 @Param("id") int userId);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND moderation_status = 'NEW' " +
            "AND p.user_id = :id",
    nativeQuery = true)
    Page<Post> getPostsWithPaginationPendingForAuth(Pageable pageable,
                                                    @Param("id") int userId);
    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND moderation_status = 'DECLINED' " +
            "AND p.user_id = :id",
            nativeQuery = true)
    Page<Post> getPostsWithPaginationDeclinedForAuth(Pageable pageable,
                                                    @Param("id") int userId);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "AND p.user_id = :id",
            nativeQuery = true)
    Page<Post> getPostsWithPaginationPublishedForAuth(Pageable pageable,
                                                     @Param("id") int userId);

    @Query(value = "SELECT * " +
            "FROM posts p2 " +
            "WHERE p2.is_active = 1 AND p2.moderation_status = 'ACCEPTED' " +
            "ORDER BY (SELECT COUNT(*) FROM post_comments pc WHERE pc.post_id = p2.id) DESC",
            countQuery = "SELECT count(*) FROM posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 'ACCEPTED'",
            nativeQuery = true)
    Page<Post> getPostsPopWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time_post` < NOW() ORDER BY `time_post`",
            countQuery = "SELECT count(*) FROM posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 'ACCEPTED'",
            nativeQuery = true)
    Page<Post> getPostsOldDateWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time_post` < NOW() ORDER BY `time_post` DESC",
            countQuery = "SELECT count(*) FROM posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 'ACCEPTED'",
            nativeQuery = true)
    Page<Post> getPostsNewDateWithPagination(Pageable pageable,
                                             @Param("moderation") Status moderationStatus);

    Page<Post> findAll(Pageable pageable);
    Post findById(int id);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time_post` < NOW();",
            nativeQuery = true)
    List<Post> getRecentPost();

    @Query(value = "SELECT * " +
            "FROM posts p2 " +
            "WHERE p2.is_active = 1 AND p2.moderation_status = 'ACCEPTED' " +
            "ORDER BY (SELECT COUNT(*) FROM post_votes pv WHERE pv.post_id = p2.id AND pv.value = 1) DESC",
            countQuery = "SELECT count(*) FROM posts WHERE `time_post` < NOW() AND is_active = 1 AND moderation_status = 'ACCEPTED'",
            nativeQuery = true)
    Page<Post> getPostsBestWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = :isActive AND p.moderation_status = :moderation AND p.`time_post` > :dayBefore " +
            "AND p.`time_post` < :dayAfter",
            nativeQuery = true)
    Page<Post> getPostDateWithPagination(@Param("isActive")int isActive,
                                         @Param("moderation") String moderationStatus,
                                         @Param("dayBefore") Date dayBefore,
                                         @Param("dayAfter") Date dayAfter, Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM posts p2 " +
            "WHERE p2.is_active = 1 AND p2.moderation_status = 'ACCEPTED' AND p2.text LIKE CONCAT ('%',:query,'%') ORDER BY `time_post` DESC",
            nativeQuery = true)
    Page<Post> findPostsWithPartOfTextWithPagination(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = :isActive AND p.moderation_status = :moderation " +
            "AND p.id = (SELECT post_id FROM tag2post WHERE tag_id = (SELECT id FROM tags WHERE name = :name)) ORDER BY `time_post` DESC",
            nativeQuery = true)
    Page<Post> getPostTagWithPagination(@Param("isActive")int isActive,
                                        @Param("moderation") String moderationStatus,
                                        @Param("name") String name,
                                        Pageable pageable);


    @Query(value = "SELECT view_count FROM posts WHERE id = :id",
            nativeQuery = true)
    int viewCount(@Param("id") int id);

    @Query(value = "SELECT * FROM posts WHERE user_id = :id",
            nativeQuery = true)
    List<Post> findPostsByUser(@Param("id") int id);

    @Query(value = "SELECT COUNT(view_count) FROM posts WHERE user_id = :id",
            nativeQuery = true)
    int findPostsViewCountByUser(@Param("id") int id);

    @Query(value = "SELECT time_post FROM posts WHERE user_id = :id ORDER BY `time_post` DESC LIMIT 1",
            nativeQuery = true)
    Date getDateFromUser(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE posts p SET p.view_count = :count WHERE p.id = :id",
    nativeQuery = true)
    int updateViewCount(@Param("count") int count,
                        @Param("id") int id);

}
