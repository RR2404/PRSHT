package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class RoleRequest {

    @FieldValidation(
            fieldName = "Role Id",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid Role Id"
    )
    private Short roleId;
}
