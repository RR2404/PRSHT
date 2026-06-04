package moe.prashast.request.pojo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.util.List;

@Data
public class ClassSectionSubmitReq {
    @FieldValidation(
            fieldName = "Year Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Integer yearId;
    @FieldValidation(
            fieldName = "School Id",
            required = true,
            minLength = 7,
            maxLength = 7
    )
    private Integer schoolId;
    @FieldValidation(
            fieldName = "Submitted Id",
            required = true,
            minLength = 7,
            maxLength = 7
    )
    private Integer submittedRoleId;

    @NotEmpty(message = "Assignments list cannot be empty")
    @Valid
    private List<AssignmentData> assignments;

    @Data
    public static class AssignmentData {
        @FieldValidation(
                fieldName = "Class Id",
                required = true,
                minLength = 1,
                maxLength = 5
        )
        private Integer classId;
        @FieldValidation(
                fieldName = "Section Id",
                required = true,
                minLength = 1,
                maxLength = 5
        )
        private Integer sectionId;
        @FieldValidation(
                fieldName = "Assigned Teacher Id",
                required = true,
                minLength = 1,
                maxLength = 10
        )
        private String assignTeacherId;
        @FieldValidation(
                fieldName = "Assign Completed Yn",
                required = true,
                minLength = 1,
                maxLength = 1
        )
        private Short assignCompletedYn;
        @FieldValidation(
                fieldName = "Assign Completed By",
                required = true,
                minLength = 1,
                maxLength = 100
        )
        private String assignCompletedBy;
    }

}
