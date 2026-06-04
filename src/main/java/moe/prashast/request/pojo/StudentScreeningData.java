package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.util.BitSet;

@Data
public class StudentScreeningData {

    @FieldValidation(
            fieldName = "Screening Id",
            required = false,
            minLength = 1,
            maxLength = 5
    )
    private Long screeningId;
    @FieldValidation(
            fieldName = "Student Id",
            required = true,
            minLength = 1,
            maxLength = 15
    )
    private Integer studentId;
    @FieldValidation(
            fieldName = "Screening Done YN",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short screeningDoneYn;
    @FieldValidation(
            fieldName = "Screening Not Done YN",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short screeningNotDoneReason;

    @FieldValidation(
            fieldName = "Screening Question Count",
            required = true,
            minLength = 1,
            maxLength = 5
    )
    private Short screeningQuestionCount;
    @FieldValidation(
            fieldName = "No Concern YN",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short noConcernYn;
    @FieldValidation(
            fieldName = "Eligible Part2 YN",
            required = true,
            minLength = 1,
            maxLength = 2
    )
    private Short eligiblePart2Yn;
    @FieldValidation(
            fieldName = "Screening Answers",
            required = true
    )
    private String screeningAnswers;
}
