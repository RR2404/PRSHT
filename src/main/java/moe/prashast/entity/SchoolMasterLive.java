package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "school_master_live_prst", schema = "public")
@IdClass(SchoolMasterLiveId.class)
public class SchoolMasterLive {

    @Id
    @Column(name = "school_id")
    private Integer schoolId;

    @Id
    @Column(name = "year_id")
    private Short yearId;

    @Column(name = "udise_sch_code")
    private String udiseSchCode;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "state_cd")
    private String stateCd;

    @Column(name = "district_cd")
    private String districtCd;

    @Column(name = "block_cd")
    private String blockCd;

    @Column(name = "cluster_cd")
    private String clusterCd;

    @Column(name = "state_id")
    private Short stateId;

    @Column(name = "district_id")
    private Short districtId;

    @Column(name = "block_id")
    private Integer blockId;

    @Column(name = "cluster_id")
    private Integer clusterId;

    @Column(name = "sch_category_id")
    private Short schCategoryId;

    @Column(name = "sch_type")
    private Short schType;

    @Column(name = "sch_mgmt_id")
    private Short schMgmtId;

    @Column(name = "sch_mgmt_center_id")
    private Short schMgmtCenterId;

    @Column(name = "class_frm")
    private Short classFrm;

    @Column(name = "class_to")
    private Short classTo;

    @Column(name = "ppsec_yn")
    private Short ppsecYn;

    @Column(name = "ppsec_cls_frm")
    private Short ppsecClsFrm;

    @Column(name = "school_status")
    private Short schoolStatus;

    @Column(name = "is_cwsn")
    private Short isCwsn;

    @Column(name = "head_master_name")
    private String headMasterName;

    @Column(name = "head_mst_type")
    private Short headMstType;

    @Column(name = "hm_mobile")
    private String hmMobile;

    @Column(name = "session_start_date")
    private LocalDate sessionStartDate;

    @Column(name = "session_end_date")
    private LocalDate sessionEndDate;

    @Column(name = "org_state_id")
    private Integer orgStateId;

    @Column(name = "org_district_id")
    private Integer orgDistrictId;

    @Column(name = "org_block_id")
    private Integer orgBlockId;

    @Column(name = "pincode")
    private String pinCode;

    @Column(name = "address")
    private String address;

    @Column(name = "total_classes")
    private Integer totalClasses;

    @Column(name = "total_sections")
    private Integer totalSections;

    @Column(name = "total_teachers")
    private Integer totalTeachers;

    @Column(name = "total_students")
    private Integer totalStudents;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Column(name="data_imported_at")
    private LocalDateTime dataImported;

    @Column(name="sch_r_u")
    private Integer locationType;

    @Column(name = "email")
    private String emailId;

}
