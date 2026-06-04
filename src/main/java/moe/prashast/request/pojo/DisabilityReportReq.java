package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class DisabilityReportReq {

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Integer yearId;

    @FieldValidation(
            fieldName = "User Id",
            required = true
    )
    private Long userId;

    @FieldValidation(
            fieldName = "Role Id",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid role id"
    )
    private Integer roleId;

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    private Integer schoolId;

    @FieldValidation(
            fieldName = "Report Id",
            required = true
    )
    private Integer  reportId;
}
