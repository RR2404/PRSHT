package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "hierarchy_master", schema = "public")
@Getter
@Setter
public class HierarchyMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hierarchy_id")
    private Long hierarchyId;

    @Column(name = "year_id", nullable = false)
    private Integer yearId;

    @Column(name = "hierarchy_level", nullable = false)
    private Short hierarchyLevel;

    @Column(name = "hierarchy_name", nullable = false, length = 255)
    private String hierarchyName;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "state_id")
    private Short stateId;

    @Column(name = "state_name", length = 100)
    private String stateName;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name", length = 100)
    private String districtName;

    @Column(name = "block_id")
    private Integer blockId;

    @Column(name = "block_name", length = 100)
    private String blockName;

    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "school_name", length = 255)
    private String schoolName;

    @Column(name = "udise_sch_code", length = 11)
    private String udiseSchCode;

    @Column(name = "is_active", nullable = false)
    private Short isActive = 1;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}
