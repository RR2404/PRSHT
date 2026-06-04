package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class CheckMobileRole {

    @FieldValidation(
            fieldName = "Mobile number",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;

    @FieldValidation(
            fieldName = "Role",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid role"
    )
    private Short role;
}
