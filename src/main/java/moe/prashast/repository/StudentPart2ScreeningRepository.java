package moe.prashast.repository;

import moe.prashast.dto.ScreeningPart2Response;
import moe.prashast.entity.StudentPart2Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentPart2ScreeningRepository extends JpaRepository<StudentPart2Screening,Long> {

    Optional<StudentPart2Screening> findByStudentIdAndSchoolIdAndYearId(Integer studentId, Integer schoolId, Integer yearId);

    List<StudentPart2Screening> findBySchoolIdAndYearIdAndClassIdAndSectionId(Integer schoolId, Integer yearId, Integer classId, Integer sectionId);

    Optional<StudentPart2Screening> findBySchoolIdAndYearIdAndClassIdAndSectionIdAndStudentId(Integer schoolId, Integer yearId, Integer classId, Integer sectionId, Integer studentId);
    
    
    @Query("""
    	    SELECT s.stateId,
    	           s.schoolId,
    	           s.yearId,
    	           s.studentId,
    	           s.studentPen,
    	           s.studentName,
    	           s.motherName,
    	           s.fatherName,
    	           s.guardianName,
    	           s.classId,
    	           s.sectionId,
    	           s.gender,
    	           s.studentDob,
    	           s.screeningDoneYn,
    	           s.disabilityTypeIdsPart1,
    	           s.disabilityTypeIds,
    	           s.disabilityTypeIdsPart2
    	    FROM StudentPart2Screening s
    	    WHERE s.schoolId = :schoolId
    	      AND s.yearId = :yearId
    	""")
    List<Object[]> findBySchoolIdAndYearId(Integer schoolId, Integer yearId);

	List<StudentPart2Screening> findBySchoolIdAndYearIdAndStudentId(Integer schoolId, Integer integer, Integer studentId);

	List<StudentPart2Screening> findBySchoolIdAndClassIdInAndSectionIdIn(Integer schoolId, List<Short> classIds, List<Short> sectionIds);
}
