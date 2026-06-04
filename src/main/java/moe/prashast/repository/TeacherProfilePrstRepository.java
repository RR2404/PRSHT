package moe.prashast.repository;

import moe.prashast.entity.TeacherProfilePrst;
import moe.prashast.entity.TeacherProfilePrstId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherProfilePrstRepository
        extends JpaRepository<TeacherProfilePrst, TeacherProfilePrstId> {

    Optional<TeacherProfilePrst> findByEmpStaffIdAndYearId(Long empStaffId, Short yearId);

    Optional<TeacherProfilePrst> findByEmpStaffId(Long empStaffId);

    List<TeacherProfilePrst> findBySchoolId(Integer schoolId);

    List<TeacherProfilePrst> findByYearId(Short yearId);

    List<TeacherProfilePrst>findBySchoolIdAndIsAssigned(Integer schoolId, Integer isAssigned);

    Optional<TeacherProfilePrst> findByMobile(String mobileNo);


    List<TeacherProfilePrst> findBySchoolIdAndYearIdAndIsAssigned(Integer schoolId, Short yearId, int i);

    List<TeacherProfilePrst> findBySchoolIdAndYearIdAndTchNameAndMobile(Integer schoolId, Integer yearId, String name, String mobile);

    List<TeacherProfilePrst> findBySchoolIdAndYearIdAndTchNameOrSchoolIdAndYearIdAndMobile(Integer schoolId, Integer yearId, String name, Integer schoolId1, Integer yearId1, String mobile);
}
