package main.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes,Integer> {
}
