package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SESpecialistMobileReq {

    @FieldValidation(
            fieldName = "Mobile",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    private Integer schoolId;
}
