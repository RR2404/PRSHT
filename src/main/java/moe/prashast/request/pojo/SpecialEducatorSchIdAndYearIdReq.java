package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SpecialEducatorSchIdAndYearIdReq {

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    private Integer schoolId;

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Short yearId;
}
