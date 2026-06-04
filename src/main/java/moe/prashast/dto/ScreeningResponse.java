package moe.prashast.dto;

import lombok.Data;
import moe.prashast.entity.StudentPart1Screening;
import moe.prashast.util.CommonUtil;

import java.time.LocalDate;
import java.util.BitSet;

@Data
public class ScreeningResponse {
    private Long screeningId;
    private Short stateId;
    private Integer studentId;
    private Integer yearId;
    private String studentName;
    private String studentPen;
    private String motherName;
    private String fatherName;
    private Short classId;
    private Short sectionId;
    private Short gender;
    private LocalDate studentDob;
    private Short screeningDoneYn;
    private Short screeningNotDoneReason;
    private Short screeningQuestionCount;
    private Short noConcernYn;
    private Short eligiblePart2Yn;
    private String screeningAnswers;
    private Short classSectionScreeningDoneYn;
    private Integer[] disabilityTypeIds;
    private int[] screeningAnswerIndex;

    public static ScreeningResponse convertToResponse(StudentPart1Screening s) {

        ScreeningResponse r = new ScreeningResponse();

        r.setScreeningId(s.getScreeningId());
        r.setStateId(s.getStateId());
        r.setStudentId(s.getStudentId());
        r.setYearId(s.getYearId());
        r.setStudentName(s.getStudentName());
        r.setStudentPen(s.getStudentPen());
        r.setMotherName(s.getMotherName());
        r.setFatherName(s.getFatherName());
        r.setClassId(s.getClassId());
        r.setSectionId(s.getSectionId());
        r.setGender(s.getGender());
        r.setStudentDob(s.getStudentDob());
        r.setScreeningDoneYn(s.getScreeningDoneYn());
        r.setScreeningNotDoneReason(s.getScreeningNotDoneReason());
        r.setScreeningQuestionCount(s.getScreeningQuestionCount());
        r.setNoConcernYn(s.getNoConcernYn());
        r.setEligiblePart2Yn(s.getEligiblePart2Yn());
        r.setScreeningAnswers(s.getScreeningAnswers());
        r.setClassSectionScreeningDoneYn(s.getClassSectionScreeningDoneYn());
        r.setDisabilityTypeIds(s.getDisabilityTypeIds());
        
        int[] indexes = java.util.stream.IntStream.range(0, s.getScreeningAnswers().length())
                .filter(i -> s.getScreeningAnswers().charAt(i) == '1')
                .toArray();
        
        r.setScreeningAnswerIndex(indexes);
        
        return r;
    }
}
