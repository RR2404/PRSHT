package moe.prashast.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentPart1ScreeningDto {


    private Integer schoolId;
    private Integer yearId;
    private Integer studentId;
    private String studentPen;
    private String studentName;
    private String motherName;
    private String fatherName;
    private String guardianName;
    private Short classId;
    private Short sectionId;
    private Short gender;
    private LocalDate studentDob;
    private Short screeningDoneYn;
    private Short screeningNotDoneReason;
    private Short screeningQuestionCount;
    private Short noConcernYn;
    private Short eligiblePart2Yn;
    private String screeningBy;
    private List<Integer> selectedAnswerIndexes;
    private Short classSectionScreeningDoneYn;
}
