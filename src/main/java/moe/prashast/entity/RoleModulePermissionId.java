package moe.prashast.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
@Data
@Embeddable
public class RoleModulePermissionId implements Serializable {

    private Short roleId;
    private Short moduleId;
}
