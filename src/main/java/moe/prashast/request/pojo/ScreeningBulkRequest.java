package moe.prashast.request.pojo;

import jakarta.validation.Valid;
import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.util.List;

@Data
public class ScreeningBulkRequest {
    @FieldValidation(
            fieldName = "State Id",
            required = true,
            minLength = 1,
            maxLength = 3
    )
    private Short stateId;
    @FieldValidation(
            fieldName = "School Id",
            required = true,
            minLength = 7,
            maxLength = 7
    )
    private Integer schoolId;
    @FieldValidation(
            fieldName = "Year Id",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Integer yearId;
    @FieldValidation(
            fieldName = "Class Id",
            required = true,
            minLength = 1,
            maxLength = 5
    )
    private Short classId;
    @FieldValidation(
            fieldName = "Section Id",
            required = true,
            minLength = 1,
            maxLength = 5
    )
    private Short sectionId;
    @FieldValidation(
            fieldName = "Class Section Screening Done yn",
            required = false,
            minLength = 1,
            maxLength = 2
    )
    private Short classSectionScreeningDoneYn;

    @Valid
    private List<StudentScreeningData> students;


}
