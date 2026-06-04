package moe.prashast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moe.prashast.entity.RoleMaster;
import moe.prashast.entity.User;
import moe.prashast.entity.UserRoleMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String mobile;
    private String udiseCode;
    private String fullName;
    private List<Short> roles;
    private Short status;
    private Integer schoolId;   // for role 1
    private Long teacherId;
    private Short stateId;
    private Long specialEducatorId;
//    private Long schoolId2;
    private Map<String, ModulePermissionDto> moduleMap;
    private List<UserRoleEntity> roleEntityList;
    private Long parentId;


    public UserDto(User user, List<RoleMaster> roleList,Map<String, ModulePermissionDto> moduleMap,
                   List<UserRoleEntity> roleEntityList) {
        this.mobile = user.getPhoneMobile();
        this.fullName = user.getName();
        this.status = user.getIsActive();
        this.schoolId = user.getSchoolId();
        this.stateId=user.getStateId();

        for (UserRoleMap urm : user.getUserRole()) {

            if (urm.getRole().getRoleId() == 2) {

                this.teacherId = urm.getRoleEntityId();

            } else if (urm.getRole().getRoleId() == 3) {
                this.specialEducatorId =urm.getRoleEntityId();
            }
        }
        this.udiseCode = user.getUdiseSchCode();
        this.roles = roleList.stream().map(RoleMaster::getRoleId).toList();
//        this.roles = user.getUserRole().stream() .map(urm -> urm.getRole().getRoleId()).toList();
        this.moduleMap=moduleMap;
        this.roleEntityList=roleEntityList;
        this.parentId=user.getParentId();
    }


}
