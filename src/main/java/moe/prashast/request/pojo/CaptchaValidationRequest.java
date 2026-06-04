package moe.prashast.request.pojo;

import lombok.Data;

@Data
public class CaptchaValidationRequest {

    private String captchaId;
    private String captchaValue;
}
