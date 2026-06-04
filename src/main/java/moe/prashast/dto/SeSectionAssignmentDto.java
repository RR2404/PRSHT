package moe.prashast.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class SeSectionAssignmentDto {

    private Long specialEducatorAssignmentId;
    private Short yearId;
    private Integer schoolId;
    private Integer classId;
    private Integer sectionId;
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

}
