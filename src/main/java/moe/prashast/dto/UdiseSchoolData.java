package moe.prashast.dto;

import lombok.Data;

@Data
public class UdiseSchoolData {

    private String udiseCode;
    private String schoolName;

    private String eduStateCode;
    private String eduDistrictCode;
    private String eduBlockCode;
    private String eduClusterCode;

    private Integer lgdStateId;
    private Integer lgdDistrictId;
    private Integer lgdBlockId;

    private Integer schCategoryId;
    private Integer schTypeId;
    private Integer schMgmtId;
    private Integer schMgmtCenterId;

    private Integer lowestClass;
    private Integer highestClass;

    private Integer prePrimaryAvailability;
    private Integer prePrimaryClassFrom;

    private Integer schStatusId;

    private String hosName;
    private String hosMobile;

    private String address;
    private String pinCode;

    private String  email;
}
