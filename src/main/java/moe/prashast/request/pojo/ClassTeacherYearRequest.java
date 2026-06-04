package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class ClassTeacherYearRequest {
    @FieldValidation(
            fieldName = "Teacher Id",
            required = true,
            minLength = 1,
            maxLength = 10
    )
    private String teacherId;

    @FieldValidation(
            fieldName = "Year Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short yearId;
}
