package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SignUpPojo {

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
    @FieldValidation(
            fieldName = "Full name",
            required = true,
            minLength = 1,
            maxLength = 30,
            regex = "^[A-Za-z .]+$",
            regexMessage = "Name must contain only letters, dot and spaces"
    )
    private String fullName;
    @FieldValidation(
            fieldName = "Role",
            required = true,
            minLength = 1,
            maxLength = 2,
            regex = "\\d+"
    )
    private Short role;

}
