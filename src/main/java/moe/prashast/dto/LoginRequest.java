package moe.prashast.dto;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class LoginRequest {
    @FieldValidation(
            fieldName = "Mobile number",
            required = false,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;

    @FieldValidation(
            fieldName = "UDISE code",
            required = false,
            minLength = 11,
            maxLength = 11,
            regex = "\\d+"
    )
    private String udiseCode;

    @FieldValidation(
            fieldName = "Password",
            required = true
    )
    private String password;

    @FieldValidation(
            fieldName = "Role",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid role"
    )
    private Short role;

    @FieldValidation(
            fieldName = "Captcha Id",
            required = true
    )
    private String captchaId;

    @FieldValidation(
            fieldName = "Captcha Value",
            required = true,
            minLength = 6,
            maxLength = 6,
            regex = "^[A-Za-z0-9]{6}$"
    )
    private String captchaValue;
}
