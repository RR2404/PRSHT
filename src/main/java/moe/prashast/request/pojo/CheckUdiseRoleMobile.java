package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class CheckUdiseRoleMobile {
    @FieldValidation(
            fieldName = "Mobile number",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
	private String mobile;

    @FieldValidation(
            fieldName = "UDISE code",
            required = true,
            minLength = 11,
            maxLength = 11,
            regex = "\\d+"
    )
	private String udise;

    @FieldValidation(
            fieldName = "Role Id",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid Role Id"
    )
	private Short roleId;
}
