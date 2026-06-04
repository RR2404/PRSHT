package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class EditUserRequest {

    @FieldValidation(
            fieldName = "User Id",
            required = true,
            minLength = 1,
            maxLength = 10
    )
    private Long userId;
    @FieldValidation(
            fieldName = "Name",
            required = true,
            minLength = 2,
            maxLength = 100,
            regex = "^[A-Za-z ]+$",
            regexMessage = "Teacher name must contain only letters, dot and spaces"
    )
    private String name;
    @FieldValidation(
            fieldName = "Mobile number",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;
    @FieldValidation(
            fieldName = "Email",
            required = true,
            minLength = 20,
            maxLength = 8,
            regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            regexMessage = "Please enter a valid email address"
    )
    private String email;
}
