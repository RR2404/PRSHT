package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.BitSet;

@Getter
@Setter
@Entity
@Table(
        name = "student_part1_screening",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_student_part1_screening",
                        columnNames = {"state_id", "student_id", "year_id"}
                )
        }
)
public class StudentPart1Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private Long screeningId;

    @Column(name = "state_id")
    private Short stateId;

    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "year_id")
    private Integer yearId;

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "student_pen", length = 20)
    private String studentPen;

    @Column(name = "student_name", length = 150)
    private String studentName;

    @Column(name = "mother_name", length = 100)
    private String motherName;

    @Column(name = "father_name", length = 100)
    private String fatherName;

    @Column(name = "guardian_name", length = 100)
    private String guardianName;

    @Column(name = "class_id")
    private Short classId;

    @Column(name = "section_id")
    private Short sectionId;

    @Column(name = "gender")
    private Short gender;

    @Column(name = "student_dob")
    private LocalDate studentDob;

    @Column(name = "screening_done_yn")
    private Short screeningDoneYn;

    @Column(name = "screening_not_done_reason")
    private Short screeningNotDoneReason;

    @Column(name = "screening_question_count")
    private Short screeningQuestionCount;

    @Column(name = "no_concern_yn")
    private Short noConcernYn = 0;

    @Column(name = "eligible_part2_yn")
    private Short eligiblePart2Yn ;

    @Column(name = "screening_by", length = 50)
    private String screeningBy;

    @Column(name = "screening_on")
    private LocalDateTime screeningOn;

    @Column(name = "screening_answers", columnDefinition = "bit(128)", nullable = false)
    @ColumnTransformer(write = "?::bit(128)")
    private String screeningAnswers;

    @Column(name = "cls_sec_screening_done_yn")
    private Short classSectionScreeningDoneYn;
    
    @Column(name = "disability_type_ids", columnDefinition = "integer[]")
    private Integer[] disabilityTypeIds;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}
