package moe.prashast.repository;

import moe.prashast.entity.StudentPart1Screening;
import moe.prashast.service.SESchoolScreeningStatusProjection;
import moe.prashast.service.SchoolReportDashboardSummaryProjection;
import moe.prashast.service.TeacherSectionScreeningStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface StudentPart1ScreeningRepository extends JpaRepository<StudentPart1Screening,Long> {
    List<StudentPart1Screening> findBySchoolIdAndYearId(Integer schoolId, Integer yearId);


    Optional<StudentPart1Screening> findByStateIdAndSchoolIdAndYearIdAndClassIdAndSectionIdAndStudentId(Short stateId, Integer schoolId, Integer yearId, Short classId, Short sectionId, Integer studentId);

    @Query(value = """
        SELECT *
        FROM public.get_teacher_section_screening_status(
            :yearId,
            :userId,
            :roleId,
            :stateId,
            :schoolId
        )
        """, nativeQuery = true)
    List<TeacherSectionScreeningStatusProjection> getScreeningStatus(
            Integer yearId,
            String userId,
            Integer roleId,
            Integer stateId,
            Integer schoolId);

    List<StudentPart1Screening> findBySchoolIdAndYearIdAndEligiblePart2Yn(Integer schoolId, Integer yearId, short i);

    List<StudentPart1Screening> findBySchoolIdAndYearIdAndClassIdAndSectionId(Integer schoolId, Integer yearId, Integer classId, Integer sectionId);

    int deleteByStudentIdAndSchoolIdAndYearId(Long studentId, Integer schoolId, Short yearId);

    @Query(value = """
        SELECT *
        FROM public.process_student_screening_disability(
            :yearId,
            :userId,
            :roleId,
            :schoolId,
            :studentId
        )
        """, nativeQuery = true)
    String updateStudentPart1ScreeningImpairment(
            Integer yearId,
            Long userId,
            Integer roleId,
            Integer schoolId,
            Long studentId);
    

    @Query(value = """
            select count(*) as shortlistpart2 from public.student_part1_screening where
            school_id =?1 and 
            class_id =?2 and 
            section_id =?3 and 
            no_concern_yn =?4 and 
            year_id =?5 and 
            eligible_part2_yn =?6
            """, nativeQuery = true)
      Long getShortListedForPart2(int schoolId, int classId,int sectionId, int noConcernYn, int yearId, int eligiblePart2Yn);



    @Query(value = """
        SELECT *
        FROM public.get_se_school_screening_status(
            :yearId,
            :userId,
            :roleId,
            :stateId,
            :schoolId
        )
        """, nativeQuery = true)
    List<SESchoolScreeningStatusProjection> getSeSchoolScreeningStatus(Integer yearId, String userId,
                                                                       Integer roleId, Integer stateId, Integer schoolId);



    @Query(value = """
            SELECT * FROM public.get_rpt_school_dashboard_summary(
                      :yearId,
                      :userId,
                      :roleId,
                      :screeningPartId,
                      :stateId,
                      :districtId,
                      :blockId,
                      CAST(:schBroadMgmtId AS int2[]),
                      CAST(:schBroadCatId AS int2[]),
                      CAST(:schRuralUrban AS int2[])
                 )
            """, nativeQuery = true )

    List<SchoolReportDashboardSummaryProjection> getSchoolReportDashboardSummary(
                    Integer yearId, String userId, Integer roleId, Integer screeningPartId,
            Integer stateId, Integer districtId, Integer blockId,
            Short[] schBroadMgmtId, Short[] schBroadCatId, Short[] schRuralUrban);

}
