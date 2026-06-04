package moe.prashast.repository;

import moe.prashast.entity.SchoolClassSectionDetailsEntity;
import moe.prashast.entity.SchoolClassSectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolClassSectionDetailsRepository
        extends JpaRepository<SchoolClassSectionDetailsEntity, SchoolClassSectionId> {

    @Procedure(name = "SchoolClassSectionDetails.fetchDetails")
    List<SchoolClassSectionDetailsEntity> fetchDetails(
            @Param("p_year_id") Integer yearId,
            @Param("p_user_id") String userId,
            @Param("p_role_id") String roleId,
            @Param("p_state_id") Integer stateId,
            @Param("p_school_id") Integer schoolId
    );
}
