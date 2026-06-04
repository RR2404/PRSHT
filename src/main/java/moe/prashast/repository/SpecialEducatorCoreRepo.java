package moe.prashast.repository;

import moe.prashast.entity.SpecialEducatorCore;
import moe.prashast.entity.SpecialEducatorCoreId;
import moe.prashast.entity.TeacherProfileCore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialEducatorCoreRepo  extends JpaRepository<SpecialEducatorCore, SpecialEducatorCoreId> {
    List<SpecialEducatorCore> findByIdSchoolIdAndIdYearId(Integer schoolId, Short yearId);

    List<SpecialEducatorCore> findByIdSchoolId(Integer schoolId);

    SpecialEducatorCore findByIdEmpStaffId(Long aLong);

    Optional<SpecialEducatorCore> findByMobile(String mobile);

}
