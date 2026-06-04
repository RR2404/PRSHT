package moe.prashast.validation;

import moe.prashast.constant.ValidationMessage;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CommonValidation {

    public String udiseCodeValidation(String udiseCode) {

        if (udiseCode == null || udiseCode.trim().isEmpty()) {
            return ValidationMessage.UDISE_REQUIRED;
        }

        if (!udiseCode.matches("\\d{11}")) {
            return ValidationMessage.INVALID_UDISE_CODE;
        }

        return null;
    }

    public String mobileNoValidation(String mobileNo) {

        if (mobileNo == null || mobileNo.trim().isEmpty()) {
            return ValidationMessage.MOBILE_REQUIRED;
        }

        if (!mobileNo.matches("\\d{10}")) {
            return ValidationMessage.INVALID_MOBILE;
        }

        return null;
    }

    public String checkName(String fullName) {

        if (fullName == null || fullName.trim().isEmpty()) {
            return ValidationMessage.NAME_REQUIRED;
        }

        return null;
    }

    public String checkRole(Short role) {

        if (role == null) {
            return ValidationMessage.ROLE_REQUIRED;
        }

        if (!Set.of((short) 1, (short) 2, (short) 3, (short) 11, (short) 12, (short) 13, (short) 14, (short) 15)
                .contains(role)) {
            return ValidationMessage.INVALID_ROLE;
        }

        return null;
    }

    public String passwordValidation(String password, String retypePassword) {
        if (password == null || password.trim().isEmpty()) {
            return ValidationMessage.PASSWORD_REQUIRED;

        }

        if (retypePassword == null || retypePassword.trim().isEmpty()) {
            return ValidationMessage.PASSWORD_REQUIRED;

        }

        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
        if (!password.matches(regex)) {
            return ValidationMessage.PASSWORD_PATTERN;
        }

        return null;
    }

    public String passwordFieldCheck(String password) {
        if (password == null || password.trim().isEmpty()) {
            return ValidationMessage.PASSWORD_REQUIRED;
        }

        return null;
    }

    public String captchaFieldCheck(String captcha) {
        if (captcha == null || captcha.trim().isEmpty()) {
            return ValidationMessage.CAPTCHA_REQUIRED;
        }

        if (!captcha.matches("[A-Za-z0-9]{6}")) {
            return ValidationMessage.INVALID_CAPTCHA;
        }

        return null;
    }
}