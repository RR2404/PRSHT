package moe.prashast.service;

import java.time.LocalDate;

public interface SESchoolScreeningStatusProjection {

    Integer getSeAssignmentId();
    String getSeId();
    String getSeName();
    Integer getClassId();
    Integer getSectionId();
    String getSectionDetails();
    LocalDate getAssignEndDate();
    String getAssignEndStatus();
    Integer getIsDeadover();
    Integer getEligiblePart2Count();
    Integer getPart2Screened();
    Integer getPendingCount();
    String getScreeningStatus();
    Integer getExecStatus();
    String getErrorDescription();


}
