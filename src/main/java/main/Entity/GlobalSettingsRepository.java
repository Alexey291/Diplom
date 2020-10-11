package main.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {
    @Query(value = "SELECT * FROM global_settings p WHERE p.id = '2'",
            nativeQuery = true)
    GlobalSettings getRecentSettings();
}
