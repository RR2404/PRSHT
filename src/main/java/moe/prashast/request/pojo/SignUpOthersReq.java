package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SignUpOthersReq {

    @FieldValidation(
            fieldName = "Udise Code",
            required = false,
            minLength = 11,
            maxLength = 11,
            regex = "\\d+"
    )
    private Long code;
    @FieldValidation(
            fieldName = "Mobile number",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;
    @FieldValidation(
            fieldName = "Role",
            required = true,
            minLength = 1,
            maxLength = 2,
            regex = "\\d+"
    )
    private Short role;
    @FieldValidation(
            fieldName = "Name",
            required = true,
            minLength = 1,
            maxLength = 30,
            regex = "^[A-Za-z .]+$",
            regexMessage = "Name must contain only letters, dot and spaces"
    )
    private String name;
    @FieldValidation(
            fieldName = "Hierarchy Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Long hierarchyId;
    @FieldValidation(
            fieldName = "Parent Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Long parentId;
    @FieldValidation(
            fieldName = "Gender",
            required = true,
            minLength = 1,
            maxLength = 8,
            regex = "^[A-Za-z]+$"
    )
    private String gender;
    @FieldValidation(
            fieldName = "Email",
            required = true,
            minLength = 8,
            maxLength = 50,
            regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            regexMessage = "Please enter a valid email address"
    )
    private String email;
}
