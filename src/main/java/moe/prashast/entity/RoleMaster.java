package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role_master")
public class RoleMaster {

    @Id
    @Column(name = "role_id")
    private Short roleId;

    @Column(name = "role_code", unique = true)
    private String roleCode;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "is_active")
    private Short isActive;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<UserRoleMap> userRoles;
}
