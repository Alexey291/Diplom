package main.repo;


import main.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tags,Integer> {
    @Query(value = "SELECT * FROM tags;",
            nativeQuery = true)
    List<Tags> getRecentTags();
    Tags findByName(String name);

    @Query(value = "SELECT * FROM tags t WHERE t.id = (SELECT tag_id FROM tag2post WHERE post_id = :post)",
    nativeQuery = true)
    List<Tags> getTagsForPost(@Param("post")int post);
}
