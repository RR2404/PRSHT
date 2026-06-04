package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "snapshot_student_data_core")
public class SnapshotStudentDataCore {

    @EmbeddedId
    private SnapshotStudentDataCoreId id;

    @Column(name = "state_id")
    private Short stateId;

    @Column(name = "student_pen", length = 20)
    private String studentPen;

    @Column(name = "student_name", length = 150)
    private String studentName;

    @Column(name = "gender")
    private Short gender;

    @Column(name = "student_dob")
    private LocalDate studentDob;

    @Column(name = "class_id")
    private Short classId;

    @Column(name = "section_id")
    private Short sectionId;

    @Column(name = "ac_year_id")
    private Short acYearId;

    @Column(name = "mother_name", length = 100)
    private String motherName;

    @Column(name = "father_name", length = 100)
    private String fatherName;

    @Column(name = "guardian_name", length = 100)
    private String guardianName;

    @Column(name = "mobile_no_1", length = 10)
    private String mobileNo1;

    @Column(name = "mobile_no_2", length = 10)
    private String mobileNo2;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "soc_cat_id")
    private Short socCatId;

    @Column(name = "minority_id")
    private Short minorityId;

    @Column(name = "is_bpl_yn")
    private Short isBplYn;

    @Column(name = "ews_yn")
    private Short ewsYn;

    @Column(name = "cwsn_yn")
    private Short cwsnYn;

    // PostgreSQL integer[] mapping
    @Column(name = "impairment_type", columnDefinition = "integer[]")
    private Integer[] impairmentType;

    @Column(name = "impairment_percent", precision = 5, scale = 2)
    private BigDecimal impairmentPercent;

    @Column(name = "cwsn_certificate_yn")
    private Short cwsnCertificateYn;

    @Column(name = "nat_ind_yn")
    private Short natIndYn;

    @Column(name = "admn_number")
    private String admnNumber;

    @Column(name = "student_status")
    private Short studentStatus;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}
