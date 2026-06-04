package moe.prashast.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class SpecialEducatorDataDto {

    private Long specialEducatorAssignmentId;
    private Short yearId;
    private Integer schoolId;
    private String schoolName;
    private Short classId;
    private Short sectionId;
    private String sectionName;
    private String sectionAlias;
    private Integer enrTotal;
    private Integer seEnrTotal;
    private Short isActive;
    private String specialEducatorId;
    private String specialEducatorName;
    private Short assignStatus;
    private LocalDate assignStartDate;
    private LocalDate assignEndDate;
    private Integer seStatus;
    private LocalDate schoolScreeningP1Deadline;
}
