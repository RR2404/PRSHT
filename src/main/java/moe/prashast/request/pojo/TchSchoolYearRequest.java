package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class TchSchoolYearRequest {

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
    private Short yearId;

}
