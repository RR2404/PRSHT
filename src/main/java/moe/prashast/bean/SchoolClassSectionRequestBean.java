package moe.prashast.bean;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SchoolClassSectionRequestBean {

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Integer yearId;

    @FieldValidation(
            fieldName = "User Id",
            required = false
    )
    private String userId;

    @FieldValidation(
            fieldName = "Role Id",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid role id"
    )
    private Integer roleId;

    @FieldValidation(
            fieldName = "State Id",
            required = true
    )
    private Integer stateId;

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    private Integer schoolId;
}
