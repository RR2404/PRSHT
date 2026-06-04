package moe.prashast.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnTransformer;

@Getter
@Setter
@Entity
@Table(name = "student_part2_screening")
public class StudentPart2Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id_part2")
    private Long screeningIdPart2;

    @Column(name = "state_id", nullable = false)
    private Short stateId;

    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @Column(name = "year_id", nullable = false)
    private Integer yearId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "student_pen")
    private String studentPen;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "gender")
    private Short gender;

    @Column(name = "student_dob")
    private LocalDate studentDob;

    @Column(name = "screening_by_part1")
    private String screeningByPart1;

    @Column(name = "screening_on_part1")
    private LocalDateTime screeningOnPart1;

    // PostgreSQL array
    @Column(name = "disability_type_ids_part1")
    private Integer[] disabilityTypeIdsPart1;

    // BIT VARYING → String
//    @Column(name = "screening_answers", columnDefinition = "bit(128)", nullable = false)
   
    @Column(name = "screening_answers_d1", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD1;

    @Column(name = "screening_answers_d2", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD2;

    @Column(name = "screening_answers_d3", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD3;

    @Column(name = "screening_answers_d4", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD4;

    @Column(name = "screening_answers_d5", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD5;

    @Column(name = "screening_answers_d6", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD6;

    @Column(name = "screening_answers_d7", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD7;

    @Column(name = "screening_answers_d8", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD8;

    @Column(name = "screening_answers_d9", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD9;

    @Column(name = "screening_answers_d10", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD10;

    @Column(name = "screening_answers_d11", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD11;

    @Column(name = "screening_answers_d12", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD12;

    @Column(name = "screening_answers_d13", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD13;

    @Column(name = "screening_answers_d14", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD14;

    @Column(name = "screening_answers_d15", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD15;

    @Column(name = "screening_answers_d16", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD16;

    @Column(name = "screening_answers_d17", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD17;

    @Column(name = "screening_answers_d18", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD18;

    @Column(name = "screening_answers_d19", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD19;

    @Column(name = "screening_answers_d20", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD20;

    @Column(name = "screening_answers_d21", columnDefinition = "bit(32)")
    @ColumnTransformer(write = "?::bit(32)")
    private String screeningAnswersD21;

    // Observations
    @Column(name = "observation_d1")
    private String observationD1;

    @Column(name = "observation_d2")
    private String observationD2;

    @Column(name = "observation_d3")
    private String observationD3;

    @Column(name = "observation_d4")
    private String observationD4;

    @Column(name = "observation_d5")
    private String observationD5;

    @Column(name = "observation_d6")
    private String observationD6;

    @Column(name = "observation_d7")
    private String observationD7;

    @Column(name = "observation_d8")
    private String observationD8;

    @Column(name = "observation_d9")
    private String observationD9;

    @Column(name = "observation_d10")
    private String observationD10;

    @Column(name = "observation_d11")
    private String observationD11;

    @Column(name = "observation_d12")
    private String observationD12;

    @Column(name = "observation_d13")
    private String observationD13;

    @Column(name = "observation_d14")
    private String observationD14;

    @Column(name = "observation_d15")
    private String observationD15;

    @Column(name = "observation_d16")
    private String observationD16;

    @Column(name = "observation_d17")
    private String observationD17;

    @Column(name = "observation_d18")
    private String observationD18;

    @Column(name = "observation_d19")
    private String observationD19;

    @Column(name = "observation_d20")
    private String observationD20;

    @Column(name = "observation_d21")
    private String observationD21;

    @Column(name = "disability_type_ids_part2")
    private Integer[] disabilityTypeIdsPart2;

    @Column(name = "disability_type_ids")
    private Integer[] disabilityTypeIds;

    @Column(name = "screening_done_yn")
    private Short screeningDoneYn;

    @Column(name = "screening_by")
    private String screeningBy;

    @Column(name = "screening_on")
    private LocalDateTime screeningOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}
