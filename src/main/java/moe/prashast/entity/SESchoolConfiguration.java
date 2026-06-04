package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name = "se_school_configuration")
@Data
public class SESchoolConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "se_school_configuration_id")
    private Long seSchoolConfId;

    @Column(name = "se_id", nullable = false)
    private Long specialEducatorId;

    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @Column(name = "year_id", nullable = false)
    private Short yearId;

    @Column(name = "language_id")
    private Short languageId;

    @Column(name = "module_id")
    private Short moduleId;

    // 🔹 PostgreSQL integer[] (requires hibernate-types)
    @Column(name = "module_ids", columnDefinition = "integer[]")
    private Integer[] moduleIds;

    @Column(name = "role_ids", columnDefinition = "integer[]")
    private Integer[] roleIds;

    // 🔹 bit(16) → map as String (simplest)
    @Column(name = "training_skill_flags", nullable = false)
    private String trainingSkillFlags;

    @Column(name = "training_skill_count", nullable = false)
    private Short trainingSkillCount;

    @Column(name = "is_active")
    private Short isActive;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}
