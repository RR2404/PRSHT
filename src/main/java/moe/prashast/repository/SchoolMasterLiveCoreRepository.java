package moe.prashast.repository;

import moe.prashast.entity.SchoolMasterId;
import moe.prashast.entity.SchoolMasterLiveCore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolMasterLiveCoreRepository extends JpaRepository<SchoolMasterLiveCore, SchoolMasterId> {

    Optional<SchoolMasterLiveCore> findByUdiseSchCode(String udiseSchCode);

    List<SchoolMasterLiveCore> findBySchoolIdAndYearIdAndHeadMasterNameAndHmMobile(Integer schoolId, Integer yearId, String name, String mobile);

    List<SchoolMasterLiveCore> findBySchoolId(Integer schoolId);
}
