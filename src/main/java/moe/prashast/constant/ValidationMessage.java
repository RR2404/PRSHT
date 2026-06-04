package moe.prashast.constant;

import lombok.Data;

@Data
public class ValidationMessage {

    public static final String MOBILE_REQUIRED = "Mobile number is required";
    public static final String INVALID_MOBILE = "Invalid mobile number";
    public static final String UDISE_REQUIRED = "UDISE code is required";
    public static final String INVALID_UDISE_CODE = "Invalid UDISE code" ;
    public static final String NAME_REQUIRED = "Name is required";
    public static final String ROLE_REQUIRED = "Role is required";
    public static final String INVALID_ROLE = "Invalid Role";
    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String PASSWORD_PATTERN = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character";
    public static final String CAPTCHA_REQUIRED = "Captcha is required";
    public static final String INVALID_CAPTCHA = "Invalid Captcha";
}
