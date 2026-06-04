package moe.prashast.dto;

import lombok.Data;
import moe.prashast.entity.HierarchyMaster;

@Data
public class HierarchyMasterDto {

    private Long hierarchyId;
    private Integer yearId;
    private Short hierarchyLevel;
    private String hierarchyName;
    private Long parentId;
    private Short stateId;
    private String stateName;
    private Integer districtId;
    private String districtName;
    private Integer blockId;
    private String blockName;
    private Integer schoolId;
    private String schoolName;
    private String udiseSchCode;
    private Short isActive;

    public HierarchyMasterDto(HierarchyMaster hm) {

        this.hierarchyId = hm.getHierarchyId();
        this.yearId = hm.getYearId();
        this.hierarchyLevel = hm.getHierarchyLevel();
        this.hierarchyName = hm.getHierarchyName();
        this.parentId = hm.getParentId();

        this.stateId = hm.getStateId();
        this.stateName = hm.getStateName();

        this.districtId = hm.getDistrictId();
        this.districtName = hm.getDistrictName();

        this.blockId = hm.getBlockId();
        this.blockName = hm.getBlockName();

        this.schoolId = hm.getSchoolId();
        this.schoolName = hm.getSchoolName();

        this.udiseSchCode = hm.getUdiseSchCode();

        this.isActive = hm.getIsActive();
    }
}
