package main.repo;

import main.entity.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {
    @Query(value = "SELECT * FROM global_settings p WHERE p.id = '3'",
            nativeQuery = true)
    GlobalSettings getRecentSettings();
}
