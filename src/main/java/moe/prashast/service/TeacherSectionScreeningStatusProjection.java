package moe.prashast.service;

import java.time.LocalDate;

public interface TeacherSectionScreeningStatusProjection {

    Integer getAssignmentId();
    Integer getClassId();
    Integer getSectionId();
    String getSectionDetails();
    String getAssignEndStatus();
    LocalDate getAssignEndDate();
    Integer getIsDeadover();
    Integer getTotalEnr();
    Integer getTotalScreened();
    Integer getTotalPending();
    Integer getPart2Screened();
    String getScreeningStatus();
    String getTeacherId();
    String getTeacherName();
    Integer getAbsentStudents();
    Integer getTotalAbsent();
//    Integer getPart2ShortListed();
    Integer getEligiblePart2Count();
    Integer getExecStatus();
    String getErrorDescription();
    Integer getAssignTeacherId();
    String getAssignTeacherName();
}
