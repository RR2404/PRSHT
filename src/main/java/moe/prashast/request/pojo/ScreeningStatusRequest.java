package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class ScreeningStatusRequest {
    @FieldValidation(
            fieldName = "Year Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Integer yearId;
    @FieldValidation(
            fieldName = "User Id",
            required = false,
            minLength = 1,
            maxLength = 15,
            regex = "\\d+"
    )
    private String userId;
    @FieldValidation(
            fieldName = "Role Id",
            required = true,
            minLength = 1,
            maxLength = 2,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Role Id must be one of 1, 2, 3, 11, 12, 13, 14 or 15"
    )
    private Integer roleId;
    @FieldValidation(
            fieldName = "State Id",
            required = true,
            minLength = 1,
            maxLength = 3
    )
    private Integer stateId;
    @FieldValidation(
            fieldName = "School Id",
            required = true,
            minLength = 7,
            maxLength = 7
    )
    private Integer schoolId;

}
