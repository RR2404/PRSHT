package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class UserIdRequest {
    @FieldValidation(
            fieldName = "User Id",
            required = true,
            minLength = 1,
            maxLength = 10
    )
    private Long userId;
}
