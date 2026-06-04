package moe.prashast.repository;

import moe.prashast.entity.DummyEntity;
import moe.prashast.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolClassSectionRepository
        extends JpaRepository<DummyEntity, Long> {

    List<SchoolClassSectionSummaryDto> getSummary(
            @Param("yearId") Integer yearId,
            @Param("userId") String userId,
            @Param("roleId") Integer roleId,
            @Param("stateId") Integer stateId,
            @Param("schoolId") Integer schoolId
    );

    List<SchoolClassSectionDetailsDto> getDetails(
            @Param("yearId") Integer yearId,
            @Param("userId") String userId,
            @Param("roleId") Integer roleId,
            @Param("stateId") Integer stateId,
            @Param("schoolId") Integer schoolId
    );
}
