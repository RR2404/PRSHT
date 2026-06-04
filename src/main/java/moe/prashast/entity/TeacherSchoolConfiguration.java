package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "teacher_school_configuration")
public class TeacherSchoolConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_school_configuration_id")
    private Long teacherSchoolConfigurationId;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @Column(name = "year_id", nullable = false)
    private Short yearId;

    @Column(name = "language_id")
    private Short languageId;

    @Column(name = "module_id")
    private Short moduleId;

    @Column(name = "module_ids")
    private Integer[] moduleIds;

    @Column(name = "role_ids")
    private Integer[] roleIds;

    @Column(name = "training_skill_flags", columnDefinition = "bit(16)")
    @ColumnTransformer(write = "?::bit(16)")
    private String trainingSkillFlags;

    @Column(name = "training_skill_count")
    private Short trainingSkillCount;

    @Column(name = "is_active")
    private Short isActive = 1;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}