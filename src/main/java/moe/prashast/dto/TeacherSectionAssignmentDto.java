package moe.prashast.dto;

import lombok.Data;
import moe.prashast.entity.TeacherSectionAssignment;

import java.time.LocalDate;
@Data
public class TeacherSectionAssignmentDto {

    private Long assignmentId;
    private Short yearId;
    private Integer schoolId;
    private Short classId;
    private Short sectionId;
    private String sectionName;
    private String sectionAlias;
    private Short enrTotal;
    private Short isActive;
    private String classTeacherId;
    private String classTeacherName;
    private String assignTeacherId;
    private String assignTeacherName;
    private Short assignStatus;
    private LocalDate assignStartDate;
    private LocalDate assignEndDate;


    public static TeacherSectionAssignmentDto convertToDto(TeacherSectionAssignment entity) {

        TeacherSectionAssignmentDto dto = new TeacherSectionAssignmentDto();

        dto.setAssignmentId(entity.getAssignmentId());
        dto.setYearId(entity.getYearId());
        dto.setSchoolId(entity.getSchoolId());
        dto.setClassId(entity.getClassId());
        dto.setSectionId(entity.getSectionId());
        dto.setSectionName(entity.getSectionName());
        dto.setSectionAlias(entity.getSectionAlias());
        dto.setEnrTotal(entity.getEnrTotal());
        dto.setIsActive(entity.getIsActive());
        dto.setClassTeacherId(entity.getClassTeacherId());
        dto.setClassTeacherName(entity.getClassTeacherName());
        dto.setAssignTeacherId(entity.getAssignTeacherId());
        dto.setAssignTeacherName(entity.getAssignTeacherName());
        dto.setAssignStatus(entity.getAssignStatus());
        dto.setAssignStartDate(entity.getAssignStartDate());
        dto.setAssignEndDate(entity.getAssignEndDate());

        return dto;
    }
}
