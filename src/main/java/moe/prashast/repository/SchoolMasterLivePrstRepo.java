package moe.prashast.repository;

import moe.prashast.entity.SchoolMasterId;
import moe.prashast.entity.SchoolMasterLivePrst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SchoolMasterLivePrstRepo extends JpaRepository<SchoolMasterLivePrst, SchoolMasterId> {
    Optional<SchoolMasterLivePrst> findByUdiseSchCode(String udiseCode);

    SchoolMasterLivePrst findBySchoolId(Integer schoolId);

    List<SchoolMasterLivePrst> findBySchoolIdIn(Set<Integer> schoolIds);

    List<SchoolMasterLivePrst> findBySchoolIdInAndYearId(Set<Integer> schoolIds, short yearId);
}