package moe.prashast.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldValidator implements ConstraintValidator<FieldValidation, Object> {

    private String fieldName;
    private boolean required;
    private int minLength;
    private int maxLength;
    private String regex;
    private String regexMessage;

    @Override
    public void initialize(FieldValidation annotation) {

        this.fieldName = annotation.fieldName();
        this.required = annotation.required();
        this.minLength = annotation.minLength();
        this.maxLength = annotation.maxLength();
        this.regex = annotation.regex();
        this.regexMessage = annotation.regexMessage();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (required) {
            if (obj == null) {

                context.buildConstraintViolationWithTemplate(fieldName + " is required").addConstraintViolation();
                return false;

            }

            if (obj instanceof Object[] arr) {
                if (arr.length == 0) {
                    context.buildConstraintViolationWithTemplate(fieldName + " is required").addConstraintViolation();
                    return false;
                }
                return true;
            }

            String value = String.valueOf(obj).trim();

            if (value.isEmpty()) {
                context.buildConstraintViolationWithTemplate(fieldName + " is required").addConstraintViolation();
                return false;

            }

            if (minLength > 0 && (value.length() < minLength || value.length() > maxLength)) {

                String message = (minLength == maxLength)
                        ? fieldName + " must be " + minLength + " characters"
                        : fieldName + " length must be between "
                        + minLength + " and " + maxLength;

                context.buildConstraintViolationWithTemplate(message)
                        .addConstraintViolation();

                return false;
            }

            if (regex != null && !regex.isBlank() && !value.matches(regex)) {

                String message = (regexMessage == null || regexMessage.isBlank())
                        ? fieldName + " contains invalid characters"
                        : regexMessage;

                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

                return false;
            }
        }
        return true;
    }
}