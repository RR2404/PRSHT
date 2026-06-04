package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Table(name = "special_educator_specialist")
@Data
public class SpecialEducatorSpecialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialist_id")
    private Long specialistId;

    @Column(name = "specialist_name", nullable = false, length = 150)
    private String specialistName;

    @Column(name = "mobile", nullable = false, unique = true)
    private String mobile;

    @Column(name = "email_id", length = 150)
    private String emailId;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "educator_type", nullable = false, length = 50)
    private String educatorType;

    @Column(name = "qualification_id")
    private Integer qualificationId;

    @Column(name = "experience_years")
    private Short experienceYears;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    @Column(name = "is_active")
    private Short isActive = 1;

    @Column(name = "emp_staff_id")
    private Long empStaffId;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDate createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDate modifiedTime;
}
