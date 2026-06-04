package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class TchSchoolYearStudentIdRequest {
    @FieldValidation(
            fieldName = "School Id",
            required = true,
            minLength = 7,
            maxLength = 7
    )
    private Integer schoolId;
    @FieldValidation(
            fieldName = "Year Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short yearId;
    @FieldValidation(
            fieldName = "Student Id",
            required = true,
            minLength = 7,
            maxLength = 15
    )
    private Integer studentId;
}
