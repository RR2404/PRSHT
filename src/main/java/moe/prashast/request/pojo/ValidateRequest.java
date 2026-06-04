package moe.prashast.request.pojo;



import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class ValidateRequest {
    @FieldValidation(
            fieldName = "UDISE code",
            required = true,
            minLength = 11,
            maxLength = 11,
            regex = "\\d+"
    )
    private String udiseCode;

    @FieldValidation(
            fieldName = "Mobile number",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobileNo;

    private String captchaId;
    private String captchaValue;
    @FieldValidation(
            fieldName = "Is check",
            required = true,
            minLength = 1,
            maxLength = 1
    )
    private Short isCheck;
}
