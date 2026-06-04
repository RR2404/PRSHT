package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "teacher_section_assignment")
public class TeacherSectionAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_section_assignment_seq")
    @SequenceGenerator(
            name = "teacher_section_assignment_seq",
            sequenceName = "teacher_section_assignment_assignment_id_seq",
            allocationSize = 1
    )
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "year_id", nullable = false)
    private Short yearId;

    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @Column(name = "class_id", nullable = false)
    private Short classId;

    @Column(name = "section_id", nullable = false)
    private Short sectionId;

    @Column(name = "section_name", length = 30)
    private String sectionName;

    @Column(name = "section_alias", length = 30)
    private String sectionAlias;

    @Column(name = "enr_total")
    private Short enrTotal = 0;

    @Column(name = "is_active")
    private Short isActive = 1;

    @Column(name = "class_teacher_id", nullable = false, length = 50)
    private String classTeacherId;

    @Column(name = "class_teacher_name", length = 100)
    private String classTeacherName;

    @Column(name = "assign_teacher_id", nullable = false, length = 50)
    private String assignTeacherId;

    @Column(name = "assign_teacher_name", length = 100)
    private String assignTeacherName;

    @Column(name = "assign_status")
    private Short assignStatus = 0;

    @Column(name = "assign_start_date")
    private LocalDate assignStartDate;

    @Column(name = "assign_end_date")
    private LocalDate assignEndDate;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Column(name = "assign_completed_yn")
    private Short assignCompletedYn;

    @Column(name = "assign_completed_by")
    private String assignCompletedBy;

    @Column(name = "assign_completed_time")
    private LocalDate assignCompletedTime;

    @Column(name = "hm_reviewed_yn")
    private Short hmReviewedYn;

    @Column(name = "hm_reviewed_by")
    private String hmReviewedBy;

    @Column(name = "hm_reviewed_time")
    private LocalDate hmReviewedTime;





}
