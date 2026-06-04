package moe.prashast.request.pojo;

import jakarta.validation.Valid;
import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.util.List;

@Data
public class SeSectionAssignRequest {

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    private Integer schoolId;

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Integer yearId;

    @Valid
    @FieldValidation(
            fieldName = "Assignments",
            required = true
    )
    private List<AssignmentData> assignments;

    @Data
    public static class AssignmentData {

        @FieldValidation(
                fieldName = "Class Id",
                required = true
        )
        private Integer classId;

        @FieldValidation(
                fieldName = "Section Id",
                required = true
        )
        private Integer sectionId;

        @FieldValidation(
                fieldName = "Special Educator Id",
                required = true
        )
        private String specialEducatorId;

        @FieldValidation(
                fieldName = "Special Educator Name",
                required = true
        )
        private String specialEducatorName;

        @FieldValidation(
                fieldName = "Deadline",
                required = true
        )
        private String deadline;
    }
}