package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SchoolConfigRequest {

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    public Integer yearId;

    @FieldValidation(
            fieldName = "User Id",
            required = true
    )
    public Integer userId;

    @FieldValidation(
            fieldName = "Role Id",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid role id"
    )
    public Integer roleId;

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    public Integer schoolId;
}
