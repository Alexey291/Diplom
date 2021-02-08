package main.repo;

import main.entity.Tag2post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Tag2PostRepository extends JpaRepository<Tag2post,Integer> {
    List<Tag2post> findByPostId(int id);
    Tag2post findByTagId (int id);
}
