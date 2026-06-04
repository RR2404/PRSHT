package moe.prashast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moe.prashast.entity.User;
import moe.prashast.entity.UserRoleMap;

@Data
@AllArgsConstructor
public class OtherUserDto {
    private String mobile;
    private String udiseCode;
    private String fullName;
    private Short status;
    private Integer schoolId;   // for role 1
    private Long teacherId;
    private Short stateId;
    private Long specialEducatorId;
    private Long parentId;
    private Long hierarchyId;
    private String email;
    private Long userId;
    private String regionName;

    public OtherUserDto() {
    }

    public OtherUserDto(User user) {

        this.mobile = user.getPhoneMobile();
        this.udiseCode = user.getUdiseSchCode();
        this.fullName = user.getName();
        this.status = user.getIsActive();
        this.schoolId = user.getSchoolId();
        this.stateId = user.getStateId();
        this.parentId=user.getParentId();
        this.hierarchyId= user.getHierarchyId();
        this.email=user.getEmailId();

        if (user.getUserRole() != null) {

            for (UserRoleMap urm : user.getUserRole()) {

                if (urm.getRole().getRoleId() == 2) {

                    this.teacherId = urm.getRoleEntityId();

                } else if (urm.getRole().getRoleId() == 3) {

                    this.specialEducatorId = urm.getRoleEntityId();
                }
            }
        }
        this.userId=user.getUserId();
        if(user.getHierarchyMaster()!=null){
            this.regionName=user.getHierarchyMaster().getHierarchyName();
        }
    }
}
