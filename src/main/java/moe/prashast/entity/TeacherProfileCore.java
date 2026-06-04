package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "teacher_profile_core")
public class TeacherProfileCore {

    @EmbeddedId
    private TeacherProfileCoreId id;

    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "nat_teacher_id", length = 10)
    private String natTeacherId;

    @Column(name = "tch_name", length = 150)
    private String tchName;

    @Column(name = "gender")
    private Short gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "social_cat")
    private Short socialCat;

    @Column(name = "qual_acad")
    private Short qualAcad;

    @Column(name = "mobile", length = 10)
    private String mobile;

    @Column(name = "email", length = 125)
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

    @Column(name = "is_assigned")
    private Short isAssigned = 0;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}

