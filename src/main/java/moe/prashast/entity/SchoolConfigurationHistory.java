package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "school_configuration_history")
public class SchoolConfigurationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @Column(name = "year_id", nullable = false)
    private Short yearId;

    @Column(name = "no_of_days")
    private Short noOfDays;

    @Column(name = "language_id")
    private Short languageId;

    @Column(name = "module_id")
    private Short moduleId;

    @Column(name = "module_ids", columnDefinition = "integer[]")
    private Integer[] moduleIds;

    @Column(name = "is_active")
    private Short isActive;

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

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    // PostgreSQL integer[]
    @Column(name = "role_ids", columnDefinition = "integer[]")
    private Integer[] roleIds;

    @Column(name = "screening_p1_deadline")
    private LocalDate screeningP1Deadline;
}

