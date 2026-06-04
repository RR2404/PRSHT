package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SchIdYearIdSeIdRequest {
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
            fieldName = "SpecialEducator Id",
            required = true,
            minLength = 1,
            maxLength = 20
    )
    private Long specialEducatorId;
}
