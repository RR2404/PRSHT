package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class StudentPart2ScreeningRequest {
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
            fieldName = "Student Id",
            required = true,
            minLength = 7,
            maxLength = 15
    )
    private Integer studentId;
    @FieldValidation(
            fieldName = "Disability Type Ids Part1",
            required = false
    )
    private Integer[] disabilityTypeIdsPart1;

    // Screening Answers

    @FieldValidation(
            fieldName = "Screening Answers D1",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D1 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD1;
    @FieldValidation(
            fieldName = "Screening Answers D2",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D2 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD2;
    @FieldValidation(
            fieldName = "Screening Answers D3",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D3 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD3;
    @FieldValidation(
            fieldName = "Screening Answers D4",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D4 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD4;
    @FieldValidation(
            fieldName = "Screening Answers D5",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D5 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD5;
    @FieldValidation(
            fieldName = "Screening Answers D6",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D6 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD6;
    @FieldValidation(
            fieldName = "Screening Answers D7",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D7 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD7;
    @FieldValidation(
            fieldName = "Screening Answers D8",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D8 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD8;
    @FieldValidation(
            fieldName = "Screening Answers D9",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D9 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD9;
    @FieldValidation(
            fieldName = "Screening Answers D10",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D10 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD10;
    @FieldValidation(
            fieldName = "Screening Answers D11",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D11 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD11;
    @FieldValidation(
            fieldName = "Screening Answers D12",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D12 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD12;
    @FieldValidation(
            fieldName = "Screening Answers D13",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D13 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD13;
    @FieldValidation(
            fieldName = "Screening Answers D14",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D14 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD14;
    @FieldValidation(
            fieldName = "Screening Answers D15",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D15 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD15;
    @FieldValidation(
            fieldName = "Screening Answers D16",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D16 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD16;
    @FieldValidation(
            fieldName = "Screening Answers D17",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D17 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD17;
    @FieldValidation(
            fieldName = "Screening Answers D18",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D18 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD18;
    @FieldValidation(
            fieldName = "Screening Answers D19",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D19 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD19;
    @FieldValidation(
            fieldName = "Screening Answers D20",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D20 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD20;
    @FieldValidation(
            fieldName = "Screening Answers D21",
            required = true,
            minLength = 32,
            maxLength = 32,
            regex = "^[01]{32}$",
            regexMessage = "Screening Answers D21 must contain exactly 32 bits (0 or 1)"
    )
    private String screeningAnswersD21;

    // Observations
    @FieldValidation(
            fieldName = "Observation D1",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD1;
    @FieldValidation(
            fieldName = "Observation D2",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD2;
    @FieldValidation(
            fieldName = "Observation D3",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD3;
    @FieldValidation(
            fieldName = "Observation D4",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD4;
    @FieldValidation(
            fieldName = "Observation D5",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD5;
    @FieldValidation(
            fieldName = "Observation D6",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD6;
    @FieldValidation(
            fieldName = "Observation D7",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD7;
    @FieldValidation(
            fieldName = "Observation D8",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD8;
    @FieldValidation(
            fieldName = "Observation D9",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD9;
    @FieldValidation(
            fieldName = "Observation D10",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD10;
    @FieldValidation(
            fieldName = "Observation D11",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD11;
    @FieldValidation(
            fieldName = "Observation D12",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD12;
    @FieldValidation(
            fieldName = "Observation D13",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD13;
    @FieldValidation(
            fieldName = "Observation D14",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD14;
    @FieldValidation(
            fieldName = "Observation D15",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD15;
    @FieldValidation(
            fieldName = "Observation D16",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD16;
    @FieldValidation(
            fieldName = "Observation D17",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD17;
    @FieldValidation(
            fieldName = "Observation D18",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD18;
    @FieldValidation(
            fieldName = "Observation D19",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD19;
    @FieldValidation(
            fieldName = "Observation D20",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD20;

    @FieldValidation(
            fieldName = "Observation D21",
            required = false,
            minLength = 0,
            maxLength = 500,
            regex = "^[A-Za-z0-9 ,.()/-]*$",
            regexMessage = "Observation contains invalid characters"
    )
    private String observationD21;
    @FieldValidation(
            fieldName = "Disability Type Ids Part2",
            required = false
    )
    private Integer[] disabilityTypeIdsPart2;
    @FieldValidation(
            fieldName = "Screening Done",
            required = true,
            minLength = 1,
            maxLength = 1
    )
    private Short screeningDoneYn;
    @FieldValidation(
            fieldName = "Disability Type Ids",
            required = true
    )
    private Integer[] disabilityTypeIds;

}
