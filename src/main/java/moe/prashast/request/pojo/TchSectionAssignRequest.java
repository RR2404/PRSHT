package moe.prashast.request.pojo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.util.List;

@Data
public class TchSectionAssignRequest {
    @FieldValidation(
            fieldName = "School Id",
            required = true,
            minLength = 7,
            maxLength = 7,
            regex = "\\d+"
    )
    private Integer schoolId;
    @FieldValidation(
            fieldName = "Year Id",
            required = true,
            minLength = 1,
            maxLength = 2,
            regex = "\\d+"
    )
    private Integer yearId;

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
                fieldName = "Assigned Teacher Name",
                required = true,
                minLength = 2,
                maxLength = 100,
                regex = "^[A-Za-z .]+$",
                regexMessage = "Teacher name must contain only letters and spaces"
        )
        private String assignTeacherName;
        @FieldValidation(
                fieldName = "Deadline",
                required = true
        )
        private String deadline;
    }
}
