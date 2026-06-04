package moe.prashast.repository;

import moe.prashast.entity.TeacherSectionAssignment;
import moe.prashast.request.pojo.ClassSectionSubmitReq;
import moe.prashast.request.pojo.ClassTeacherYearRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherSectionAssignmentRepository extends JpaRepository<TeacherSectionAssignment, Long> {
    boolean existsBySchoolIdAndYearId(Integer schoolId, Short yearId);

    List<TeacherSectionAssignment> findBySchoolId(Integer schoolId);

    Optional<TeacherSectionAssignment> findBySchoolIdAndClassIdAndSectionIdAndYearId(Integer schoolId, Integer classId,
            Integer sectionId, Integer yearId);
    // List<TeacherSectionAssignment>
    // findBySchoolIdAndClassIdInAndSectionIdInAndYearId( Integer schoolId,
    // List<Integer> classIds,
    // List<Integer> sectionIds, Integer yearId);

    List<TeacherSectionAssignment> findByAssignTeacherId(String teacherId);

    List<TeacherSectionAssignment> findBySchoolIdAndYearId(Integer schoolId, Short yearId);

    Optional<TeacherSectionAssignment> findBySchoolIdAndClassIdAndSectionIdAndAssignTeacherId(Integer schoolId, Integer classId, Integer sectionId, String assignTeacherId);

    List<TeacherSectionAssignment> findByAssignTeacherIdAndYearId(String teacherId, Short yearId);
}
