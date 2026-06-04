package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "school_master_live_prst")
@Data
@IdClass(SchoolMasterId.class)
public class SchoolMasterLivePrst {

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

    @Column(name = "head_master_name")
    private String headMasterName;

    @Column(name = "hm_mobile")
    private String hmMobile;

    @Column(name = "address")
    private String address;

    @Column(name = "pincode")
    private String pinCode;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Column(name = "total_classes")
    private Integer totalClass;

    @Column(name = "total_sections")
    private Integer totalScetion;

    @Column(name = "total_students")
    private Integer totalStudent;

    @Column(name = "total_teachers")
    private Integer totalTeacher;

    @Column(name="data_imported_at")
    private LocalDateTime dataImported;

    @Column(name="sch_r_u")
    private Integer locationType;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "email")
    private String emailId;


}
