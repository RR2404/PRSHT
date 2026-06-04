package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

import java.util.List;
@Data
public class  SchoolDashboardSummaryReq {

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Integer yearId;

    @FieldValidation(
            fieldName = "User Id",
            required = true
    )
    private String userId;

    @FieldValidation(
            fieldName = "Role Id",
            required = true,
            regex = "^(1|2|3|11|12|13|14|15)$",
            regexMessage = "Invalid role id"
    )
    private Integer roleId;
//    private Integer hierarchyLevel;
//    private Integer nodeId;


    @FieldValidation(
            fieldName = "Screening Part Id",
            required = true
    )
    private Integer screeningPartId;

    @FieldValidation(
            fieldName = "State Id",
            required = true
    )
    private Integer stateId;

    @FieldValidation(
            fieldName = "District Id",
            required = true
    )
    private Integer districtId;

    @FieldValidation(
            fieldName = "Block Id",
            required = true
    )
    private Integer blockId;
//    private Integer parentNodeId;

    @FieldValidation(
            fieldName = "School Broad Management Id",
            required = true
    )
    private Short[] schBroadMgmtId;

    @FieldValidation(
            fieldName = "School Broad Category Id",
            required = true
    )
    private Short[] schBroadCatId;

    @FieldValidation(
            fieldName = "School Rural Urban",
            required = true
    )
    private Short[] schRuralUrban;
}
