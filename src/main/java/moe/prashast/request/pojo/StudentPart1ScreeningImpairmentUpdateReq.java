package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.math.BigInteger;

@Data
public class StudentPart1ScreeningImpairmentUpdateReq {
    @FieldValidation(
            fieldName = "Year Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Integer yearId;
    @FieldValidation(
            fieldName = "User Id",
            required = true,
            minLength = 1,
            maxLength = 10
    )
    private Long userId;
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
            fieldName = "School Id",
            required = true,
            minLength = 7,
            maxLength = 7
    )
    private Integer schoolId;
    @FieldValidation(
            fieldName = "Student Id",
            required = true,
            minLength = 7,
            maxLength = 15
    )
    private Long studentId;
    @FieldValidation(
            fieldName = "Teacher Id",
            required = true,
            minLength = 1,
            maxLength = 10
    )
    private Long teacherId;
}
