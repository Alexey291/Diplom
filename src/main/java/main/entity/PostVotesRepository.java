package main.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes,Integer> {
    @Query(value = "SELECT id FROM post_votes WHERE time_votes = :date AND user_id= :id",
            nativeQuery = true)
    Integer getId(@Param("id") int id,
              @Param("date")Date date);
}
