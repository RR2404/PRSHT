package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SpecialEducatorReq {

    @FieldValidation(
            fieldName = "SE School Configuration Id",
            required = false
    )
    private Long seSchoolConfigurationId;

    @FieldValidation(
            fieldName = "Special Educator Id",
            required = true
    )
    private Long specialEducatorId;

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    private Integer schoolId;

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Short yearId;

    @FieldValidation(
            fieldName = "Language Id",
            required = true
    )
    private Short languageId;

    @FieldValidation(
            fieldName = "Module Id",
            required = true
    )
    private Short moduleId;

    @FieldValidation(
            fieldName = "Role Ids",
            required = true
    )
    private Integer[] roleIds;

    @FieldValidation(
            fieldName = "Training Skill Flags",
            required = true
    )
    private String trainingSkillFlags;

    @FieldValidation(
            fieldName = "Training Skill Count",
            required = true
    )
    private Short trainingSkillCount;

    @FieldValidation(
            fieldName = "Is Active",
            required = true,
            regex = "^(1|2)$",
            regexMessage = "Invalid 'Is Active' value"
    )
    private Short isActive;

    @FieldValidation(
            fieldName = "User Name",
            required = true
    )
    private String userName;
}
