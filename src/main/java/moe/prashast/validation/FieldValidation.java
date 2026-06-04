package moe.prashast.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldValidator.class)
@Documented
public @interface FieldValidation {

    String message() default "Invalid field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();

    boolean required() default false;

    int minLength() default 0;

    int maxLength() default Integer.MAX_VALUE;

    String regex() default "";
    String regexMessage() default "";
}
