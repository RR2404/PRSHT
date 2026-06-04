package moe.prashast.dto;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SetPasswordRequest {

    @FieldValidation(
            fieldName = "Mobile number",
//            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;
    @FieldValidation(
            fieldName = "UDISE code",
//            required = true,
            minLength = 11,
            maxLength = 11,
            regex = "\\d+"
    )
    private String udiseCode;
    @FieldValidation(
            fieldName = "Password",
            required = true,
            minLength = 8,
            maxLength = 20,
            regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
            regexMessage = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character"

            )
    private String password;
    @FieldValidation(
            fieldName = "Retype Password",
            required = true,
            minLength = 8,
            maxLength = 20,
            regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
            regexMessage = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character"

    )
    private String retypePassword;
}
