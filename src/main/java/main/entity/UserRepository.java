package main.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
   @Query (value = "SELECT * FROM users p WHERE p.id = 1; ",
           nativeQuery = true)
    User getUser();
}
