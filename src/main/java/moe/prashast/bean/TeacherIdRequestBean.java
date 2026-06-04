package moe.prashast.bean;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class TeacherIdRequestBean {
    @FieldValidation(
            fieldName = "Teacher Id",
            required = true,
            minLength = 1,
            maxLength = 10
    )
    private Long teacherId;
}
