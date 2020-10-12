package main.Entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tags,Integer> {
    @Query(value = "SELECT * FROM tags;",
            nativeQuery = true)
    List<Tags> getRecentTags();
}
