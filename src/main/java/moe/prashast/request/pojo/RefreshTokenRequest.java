package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class RefreshTokenRequest {

    @FieldValidation(
            fieldName = "Refresh Token",
            required = true
    )
    private String refreshToken;
}
