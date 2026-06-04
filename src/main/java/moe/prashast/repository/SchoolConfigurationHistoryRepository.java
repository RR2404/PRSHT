package moe.prashast.repository;


import moe.prashast.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolConfigurationHistoryRepository
        extends JpaRepository<SchoolConfigurationHistory, Long> {
}

