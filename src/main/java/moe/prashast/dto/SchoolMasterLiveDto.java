package moe.prashast.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SchoolMasterLiveDto {

    private Integer schoolId;
    private Short yearId;
    private String udiseSchCode;
    private String schoolName;
    private String stateCd;
    private String districtCd;
    private String blockCd;
    private String clusterCd;
    private Short stateId;
    private Short districtId;
    private Integer blockId;
    private Integer clusterId;
    private Short schCategoryId;
    private Short schType;
    private Short schMgmtId;
    private Short schMgmtCenterId;
    private Short classFrm;
    private Short classTo;
    private Short ppsecYn;
    private Short ppsecClsFrm;
    private Short schoolStatus;
    private Short isCwsn;
    private String headMasterName;
    private Short headMstType;
    private String hmMobile;
    private LocalDate sessionStartDate;
    private LocalDate sessionEndDate;
    private Integer orgStateId;
    private Integer orgDistrictId;
    private Integer orgBlockId;
    private String pinCode;
    private String address;
    private Integer totalClasses;
    private Integer totalSections;
    private Integer totalTeachers;
    private Integer totalStudents;
    private LocalDateTime dataImportedAt;
    private Integer locationType;
    private String emailId;

}

