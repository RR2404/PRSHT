package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class StudentPart1ScreeningReq {
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
    private Integer yearId;
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
}
