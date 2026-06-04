package moe.prashast.request.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.time.LocalDate;
@Data
public class SpecialEducatorSpecialistReq {

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    private Integer schoolId;

    @FieldValidation(
            fieldName = "Specialist Name",
            required = true
    )
    private String specialistName;

    @FieldValidation(
            fieldName = "Mobile",
            required = true,
            minLength = 10,
            maxLength = 10,
            regex = "\\d+"
    )
    private String mobile;

    @NotBlank(message = "Email Id is required")
    @Email(message = "Invalid email address")
    private String emailId;

    @FieldValidation(
            fieldName = "Gender",
            required = true
    )
    private Short gender;

    @NotNull(message = "Date of Birth is required")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dateOfBirth;

    @FieldValidation(
            fieldName = "Educator Type",
            required = true
    )
    private Short educatorType;

    @FieldValidation(
            fieldName = "Qualification Id",
            required = true
    )
    private Integer qualificationId;

    @FieldValidation(
            fieldName = "Experience Years",
            required = true
    )
    private Short experienceYears;

    @FieldValidation(
            fieldName = "Reference Number",
            required = true
    )
    private String referenceNumber;

    @FieldValidation(
            fieldName = "Is Active",
            required = true
    )
    private Short isActive;

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Short yearId;

}
