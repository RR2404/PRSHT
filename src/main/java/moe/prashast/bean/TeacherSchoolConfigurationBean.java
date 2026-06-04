package moe.prashast.bean;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class TeacherSchoolConfigurationBean {

    @FieldValidation(
            fieldName = "Teacher school configuration Id",
            required = false,
            minLength = 1,
            maxLength = 10
    )
    private Long teacherSchoolConfigurationId;
    @FieldValidation(
            fieldName = "Teacher Id",
            required = true,
            minLength = 1,
            maxLength = 10
    )
    private Long teacherId;
    @FieldValidation(
            fieldName = "School Id",
            required = true,
            minLength = 7,
            maxLength = 7
    )
    private Integer schoolId;
    @FieldValidation(
            fieldName = "Year Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short yearId;
    @FieldValidation(
            fieldName = "Language Id",
            required = false,
            minLength = 1,
            maxLength = 3
    )
    private Short languageId;
    @FieldValidation(
            fieldName = "Module Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short moduleId;
    @FieldValidation(
            fieldName = "Role Id",
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
            required = true,
            minLength = 1,
            maxLength = 5
    )
    private Short trainingSkillCount;
    @FieldValidation(
            fieldName = "Is Active",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short isActive;
    @FieldValidation(
            fieldName = "UserName",
            required = true,
            minLength = 2,
            maxLength = 100,
            regex = "^[0-9A-Za-z ]+$",
            regexMessage = "User name must be alphanumeric"
    )
    private String userName;

}
