package moe.prashast.repository;

import aj.org.objectweb.asm.commons.Remapper;
import jakarta.persistence.LockModeType;
import moe.prashast.dto.SpecialEducatorDetailsDto;
import moe.prashast.entity.SpecialEducatorPrst;
import moe.prashast.entity.SpecialEducatorPrstId;
import moe.prashast.entity.TeacherProfilePrst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialEducatorPrstRepo extends JpaRepository<SpecialEducatorPrst, SpecialEducatorPrstId> {
    List<SpecialEducatorPrst> findByIdSchoolId(Integer schoolId);

    List<SpecialEducatorPrst> findByIdEmpStaffId(Integer specialEducatorId);

    Optional<SpecialEducatorPrst> findByIdEmpStaffIdAndIdSchoolId(Integer specialEducatorId, Integer schoolId);
    @Query("SELECT MAX(e.id.empStaffId) FROM SpecialEducatorPrst e WHERE e.id.empStaffId >= 900000000")
    Long findLastEmpStaffIdStartingFromNine();

    List<SpecialEducatorPrst> findByIdSchoolIdAndIdYearId(Integer schoolId, Short yearId);

    Optional<SpecialEducatorPrst> findByMobileAndIdSchoolId(String mobile, Integer schoolId);

    Optional<SpecialEducatorPrst> findByMobileAndIdSchoolIdAndIdYearId(String mobile, Integer schoolId, Short yearId);

    Optional<SpecialEducatorPrst> findByIdEmpStaffId(String specialEducatorId);
}
