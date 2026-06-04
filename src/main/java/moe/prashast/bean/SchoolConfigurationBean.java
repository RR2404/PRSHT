package moe.prashast.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.time.LocalDate;
import java.util.Date;

@Data
public class SchoolConfigurationBean {

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
            fieldName = "No of days",
            required = true
    )
    private Short noOfDays;
    @FieldValidation(
            fieldName = "Language Id",
            required = true,
            minLength = 1,
            maxLength = 2
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

    private Short isActive;
    private String createdBy;
    private String modifiedBy;
    @FieldValidation(
            fieldName = "Save or Update",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Integer saveOrUpdate;
    @FieldValidation(
            fieldName = "Designated Teacher Id",
            required = false,
            minLength = 1,
            maxLength = 10
    )
    private Long designatedTeacherId;

    @FieldValidation(
            fieldName = "ScreeningP1 Deadline",
            required = false,
            minLength = 1,
            maxLength = 10
    )
    private LocalDate screeningP1Deadline;

}
