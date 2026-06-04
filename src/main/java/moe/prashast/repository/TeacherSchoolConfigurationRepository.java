package moe.prashast.repository;

import moe.prashast.entity.TeacherSchoolConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherSchoolConfigurationRepository extends JpaRepository<TeacherSchoolConfiguration, Long> {

    Optional<TeacherSchoolConfiguration>findByTeacherIdAndSchoolIdAndIsActive(Long teacherId, Integer schoolId,Short isActive);

    Optional<TeacherSchoolConfiguration>
    findByTeacherIdAndSchoolIdAndYearIdAndIsActive(Long teacherId, Integer schoolId, Short yearId, Short isActive);
}