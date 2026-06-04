package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class UdiseCodeMobileReq {
    @FieldValidation(
            fieldName = "Udise Code",
            required = true,
            minLength = 11,
            maxLength = 11,
            regex = "\\d+"
    )
    private String udiseCode;
}
