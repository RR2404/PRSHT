package moe.prashast.repository;

import moe.prashast.entity.SpecialEducatorSectionAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeSectionAssignmentRepo extends JpaRepository<SpecialEducatorSectionAssignment,Long> {

    Optional<SpecialEducatorSectionAssignment> findBySchoolIdAndClassIdAndSectionIdAndYearId(Integer schoolId, Integer i, Integer i1, short i2);

    List<SpecialEducatorSectionAssignment> findBySchoolIdAndYearId(Integer schoolId, Short yearId);

    List<SpecialEducatorSectionAssignment> findBySpecialEducatorId(String seId);
    
   
    @Modifying
    @Transactional
    @Query("""
           UPDATE SpecialEducatorSectionAssignment s
           SET s.seEnrTotal = :seEnrTotal
           WHERE s.schoolId = :schoolId 
           and s.classId = :classId
           and s.sectionId = :sectionId
           and s.yearId = :yearId
           """)
   int updateShortlistedStudentCount( @Param("seEnrTotal") Long seEnrTotal,
	        @Param("schoolId") int schoolId,
	        @Param("classId") int classId,
	        @Param("sectionId") int sectionId,
	        @Param("yearId") int yearId
		   );

    List<SpecialEducatorSectionAssignment> findBySchoolIdAndYearIdAndSpecialEducatorId(Integer schoolId, Integer yearId, Long specialEducatorId);
//   int update se_section_assignment set se_enr_total =5 where school_id= and class_id= and section_id= and year_id =
}
