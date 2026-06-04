package moe.prashast.repository;

import moe.prashast.entity.SchoolMasterLive;
import moe.prashast.entity.SchoolMasterLiveId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SchoolMasterLiveRepository extends JpaRepository<SchoolMasterLive, SchoolMasterLiveId> {

    List<SchoolMasterLive> findBySchoolId(Integer schoolId);

    Optional<SchoolMasterLive> findTopBySchoolIdOrderByYearIdDesc(Integer schoolId);

}
