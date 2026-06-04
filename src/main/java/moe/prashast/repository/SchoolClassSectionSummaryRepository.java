package moe.prashast.repository;

import moe.prashast.entity.SchoolClassSectionSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolClassSectionSummaryRepository extends JpaRepository<SchoolClassSectionSummaryEntity, Integer> {

    @Procedure(name = "SchoolClassSectionSummary.fetchSummary")
    List<SchoolClassSectionSummaryEntity> fetchSummary(
            @Param("p_year_id") Integer yearId,
            @Param("p_user_id") String userId,
            @Param("p_role_id") String roleId,
            @Param("p_state_id") Integer stateId,
            @Param("p_school_id") Integer schoolId);
}
