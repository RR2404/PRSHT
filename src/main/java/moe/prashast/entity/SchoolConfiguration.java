package moe.prashast.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "school_configuration")
public class SchoolConfiguration {

    @EmbeddedId
    private SchoolConfigurationId id;

    @Column(name = "no_of_days")
    private Short noOfDays;

    @Column(name = "language_id")
    private Short languageId;

    @Column(name = "module_id")
    private Short moduleId;

    // PostgreSQL integer[]
    @Column(name = "module_ids", columnDefinition = "integer[]")
    private Integer[] moduleIds;

    @Column(name = "is_active")
    private Short isActive = 1;

    @Column(name = "sync_status")
    private Short syncStatus = 0;

    @Column(name = "sync_requested_at")
    private LocalDateTime syncRequestedAt;

    @Column(name = "sync_expected_at")
    private LocalDateTime syncExpectedAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @UpdateTimestamp
    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    // PostgreSQL integer[]
    @Column(name = "role_ids", columnDefinition = "integer[]")
    private Integer[] roleIds;

    @Column(name = "screening_p1_deadline")
    private LocalDate screeningP1Deadline;

    @Column(name="data_imported_at")
    private LocalDateTime dataImportedAt;

    @Column(name="designated_teacher_id")
    private Long designatedTchId;

    @Column(name="screening_p2_dealine")
    private LocalDate screeningP2Deadline;

}
