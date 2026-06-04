package moe.prashast.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Entity
@Table(name = "teacher_profile_prst")
@IdClass(TeacherProfilePrstId.class)
public class TeacherProfilePrst {

    @Id
    @Column(name = "emp_staff_id", nullable = false)
    private Long empStaffId;

    @Id
    @Column(name = "year_id", nullable = false)
    private Short yearId;

    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "nat_teacher_id")
    private String natTeacherId;

    @Column(name = "tch_name")
    private String tchName;

    private Short gender;
    private LocalDate dob;

    @Column(name = "social_cat")
    private Short socialCat;

    @Column(name = "qual_acad")
    private Short qualAcad;

    @Column(name = "mobile")
    private String mobile;
    @Column(name = "email")
    private String email;

    @Column(name = "nature_of_appt")
    private Short natureOfAppt;

    @Column(name = "tch_type")
    private Short tchType;

    @Column(name = "doj_service")
    private LocalDate dojService;

    @Column(name = "class_taught")
    private Short classTaught;

    @Column(name = "trained_cwsn")
    private Short trainedCwsn;

    @Column(name = "trained_comp")
    private Short trainedComp;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Column(name = "is_assigned", columnDefinition = "SMALLINT DEFAULT 0")
    private Short isAssigned = 0;

}
