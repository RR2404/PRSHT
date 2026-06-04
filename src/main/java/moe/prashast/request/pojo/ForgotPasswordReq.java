package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class ForgotPasswordReq {

    @FieldValidation(
            fieldName = "UDISE code",
            required = false,
            minLength = 11,
            maxLength = 11,
            regex = "\\d+"
    )
    private String udiseCode;
    @FieldValidation(
            fieldName = "Mobile Number",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;

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
    @FieldValidation(
            fieldName = "Otp",
            required = true,
            minLength = 6,
            maxLength = 6,
            regex = "\\d+"
    )
    private Integer otp;
    @FieldValidation(
            fieldName = "Role",
            required = true,
            minLength = 1,
            maxLength = 2,
            regex = "\\d+"
    )
    private Short roleId;
    @FieldValidation(
            fieldName = "Verify Type",
            required = false,
            minLength = 1,
            maxLength = 2,
            regex = "\\d+"
    )
    private Short verifyType;
}
