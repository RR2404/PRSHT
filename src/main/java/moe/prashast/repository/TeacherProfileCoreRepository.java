package moe.prashast.repository;

import moe.prashast.entity.TeacherProfileCore;
import moe.prashast.entity.TeacherProfileCoreId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherProfileCoreRepository
        extends JpaRepository<TeacherProfileCore, TeacherProfileCoreId> {

    List<TeacherProfileCore> findBySchoolIdAndIdYearId(Integer schoolId, Short yearId);

    TeacherProfileCore findByIdEmpStaffId(String assignTeacherId);

    List<TeacherProfileCore> findBySchoolId(Integer schoolId);

    Optional<TeacherProfileCore> findByMobile(String mobile);
}

