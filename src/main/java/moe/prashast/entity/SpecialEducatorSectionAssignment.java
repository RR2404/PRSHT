package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "se_section_assignment")
@Data
public class SpecialEducatorSectionAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "se_assignment_id")
    private Long specialEducatorAssignmentId;

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
    private Integer enrTotal;

    @Column(name = "se_enr_total")
    private Integer seEnrTotal;

    @Column(name = "is_active")
    private Short isActive;

    @Column(name = "se_id", length = 50)
    private String specialEducatorId;

    @Column(name = "se_name", length = 100)
    private String specialEducatorName;

    @Column(name = "assign_status")
    private Short assignStatus;

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

    @Column(name = "se_status")
    private Integer seStatus;
}
