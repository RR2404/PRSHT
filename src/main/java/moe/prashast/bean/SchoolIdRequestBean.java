package moe.prashast.bean;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SchoolIdRequestBean {
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
}
