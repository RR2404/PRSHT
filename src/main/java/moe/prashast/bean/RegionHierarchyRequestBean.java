package moe.prashast.bean;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class RegionHierarchyRequestBean {

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Integer yearId;

    @FieldValidation(
            fieldName = "User Id",
            required = true
    )
    private String userId;

    @FieldValidation(
            fieldName = "Role",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid role id"
    )
    private Integer roleId;

    @FieldValidation(
            fieldName = "Region Type",
            required = true
    )
    private Integer regionType;

    @FieldValidation(
            fieldName = "Region Value",
            required = true
    )
    private String regionValue;

}
