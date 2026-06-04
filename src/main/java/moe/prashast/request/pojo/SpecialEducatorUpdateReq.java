package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SpecialEducatorUpdateReq {

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

    @FieldValidation(
            fieldName = "Mobile",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;

}
