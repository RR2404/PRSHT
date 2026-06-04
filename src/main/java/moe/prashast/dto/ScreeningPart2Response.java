package moe.prashast.dto;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import moe.prashast.entity.StudentPart1Screening;
import moe.prashast.entity.StudentPart2Screening;

import static moe.prashast.util.CommonUtil.getIndexesOfOnes;

@Data
public class ScreeningPart2Response {
  
    private Short stateId;
    private Integer schoolId;
    private Integer yearId;
    private Integer studentId;
    private String studentPen;
    private String studentName;
    private String motherName;
    private String fatherName;
    private String guardianName;
    private Integer classId;
    private Integer sectionId;
    private Short gender;
    private LocalDate studentDob;
    private String screeningByPart1;
    private LocalDateTime screeningOnPart1;
    private Integer[] disabilityTypeIdsPart1;
//    private List<Integer> screeningAnswersD1;
//    private List<Integer> screeningAnswersD2;
//    private List<Integer> screeningAnswersD3;
//    private List<Integer> screeningAnswersD4;
//    private List<Integer> screeningAnswersD5;
//    private List<Integer> screeningAnswersD6;
//    private List<Integer> screeningAnswersD7;
//    private List<Integer> screeningAnswersD8;
//    private List<Integer> screeningAnswersD9;
//    private List<Integer> screeningAnswersD10;
//    private List<Integer> screeningAnswersD11;
//    private List<Integer> screeningAnswersD12;
//    private List<Integer> screeningAnswersD13;
//    private List<Integer> screeningAnswersD14;
//    private List<Integer> screeningAnswersD15;
//    private List<Integer> screeningAnswersD16;
//    private List<Integer> screeningAnswersD17;
//    private List<Integer> screeningAnswersD18;
//    private List<Integer> screeningAnswersD19;
//    private List<Integer> screeningAnswersD20;
//    private List<Integer> screeningAnswersD21;
    private String observationD1;
    private String observationD2;
    private String observationD3;
    private String observationD4;
    private String observationD5;
    private String observationD6;
    private String observationD7;
    private String observationD8;
    private String observationD9;
    private String observationD10;
    private String observationD11;
    private String observationD12;
    private String observationD13;
    private String observationD14;
    private String observationD15;
    private String observationD16;
    private String observationD17;
    private String observationD18;
    private String observationD19;
    private String observationD20;
    private String observationD21;

    private Map<String, List<Integer>> screeningAnswers;
//    private Map<String, List<Integer>> observations;
    private Integer[] disabilityTypeIdsPart2;
    private Integer[] disabilityTypeIds;
    private Short screeningDoneYn;
    
    
    public static ScreeningPart2Response convertToResponse(Object[] obj) {

    	ScreeningPart2Response r = new ScreeningPart2Response();

    	 r.setStateId((Short) obj[0]);
    	 r.setSchoolId((Integer) obj[1]);
    	 r.setYearId((Integer) obj[2]);
    	 r.setStudentId((Integer) obj[3]);
    	 r.setStudentPen((String) obj[4]);
    	 r.setStudentName((String) obj[5]);
    	 r.setMotherName((String) obj[6]);
    	 r.setFatherName((String) obj[7]);
    	 r.setGuardianName((String) obj[8]);
    	 r.setClassId((Integer) obj[9]);
    	 r.setSectionId((Integer) obj[10]);
    	 r.setGender((Short) obj[11]);
    	 r.setStudentDob((LocalDate) obj[12]);
    	 r.setScreeningDoneYn((Short) obj[13]);
    	 r.setDisabilityTypeIdsPart1((Integer[]) obj[14]);
        r.setDisabilityTypeIds((Integer[]) obj[15]);
        r.setDisabilityTypeIdsPart2((Integer[]) obj[16]);
    	
        return r;
    }

    public static ScreeningPart2Response convertToResponse(StudentPart2Screening obj) {

        ScreeningPart2Response r = new ScreeningPart2Response();

        r.setStateId(obj.getStateId());
        r.setSchoolId(obj.getSchoolId());
        r.setYearId(obj.getYearId());
        r.setStudentId(obj.getStudentId());
        r.setStudentPen(obj.getStudentPen());
        r.setStudentName(obj.getStudentName());
        r.setMotherName(obj.getMotherName());
        r.setFatherName(obj.getFatherName());
        r.setGuardianName(obj.getGuardianName());
        r.setClassId(obj.getClassId());
        r.setSectionId(obj.getSectionId());
        r.setGender(obj.getGender());
        r.setStudentDob(obj.getStudentDob());
        r.setScreeningDoneYn(obj.getScreeningDoneYn());
        r.setDisabilityTypeIdsPart1(obj.getDisabilityTypeIdsPart1());
        r.setScreeningAnswers(getDynamicMap(obj, "getScreeningAnswersD"));
//        r.setObservations(getDynamicMap(obj, "getObservationD"));
        r.setObservationD1(obj.getObservationD1());
        r.setObservationD2(obj.getObservationD2());
        r.setObservationD3(obj.getObservationD3());
        r.setObservationD4(obj.getObservationD4());
        r.setObservationD5(obj.getObservationD5());
        r.setObservationD6(obj.getObservationD6());
        r.setObservationD7(obj.getObservationD7());
        r.setObservationD8(obj.getObservationD8());
        r.setObservationD9(obj.getObservationD9());
        r.setObservationD10(obj.getObservationD10());
        r.setObservationD11(obj.getObservationD11());
        r.setObservationD12(obj.getObservationD12());
        r.setObservationD13(obj.getObservationD13());
        r.setObservationD14(obj.getObservationD14());
        r.setObservationD15(obj.getObservationD15());
        r.setObservationD16(obj.getObservationD16());
        r.setObservationD17(obj.getObservationD17());
        r.setObservationD18(obj.getObservationD18());
        r.setObservationD19(obj.getObservationD19());
        r.setObservationD20(obj.getObservationD20());
        r.setObservationD21(obj.getObservationD21());
        r.setDisabilityTypeIds(obj.getDisabilityTypeIds());
        r.setDisabilityTypeIdsPart2(obj.getDisabilityTypeIdsPart2());

        return r;
    }

    private static Map<String, List<Integer>> getDynamicMap(Object obj, String methodPrefix) {

        Map<String, List<Integer>> result = new HashMap<>();

        try {
            for (int i = 1; i <= 21; i++) {

                String methodName = methodPrefix + i;
                Method method = obj.getClass().getMethod(methodName);

                String value = (String) method.invoke(obj);

                result.put("D" + i, getIndexesOfOnes(value));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error mapping dynamic fields", e);
        }

        return result;
    }



}
