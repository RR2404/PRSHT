package moe.prashast.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SchoolConfigurationDto {

    private Integer schoolId;
    private Short yearId;
    private Short noOfDays;
    private Short languageId;
    private Short moduleId;
    private Integer[] moduleIds;
    private String createdBy;
    private String modifiedBy;
    private Short isActive;
    private Short syncStatus;
    private LocalDateTime syncRequestedAt;
    private LocalDateTime syncExpectedAt;
    private Integer[] roleIds;
    private LocalDate screeningP1Deadline;
    private LocalDateTime dataImportedAt;
    private Long designatedTeacherId;
    private String teacherName;
}

