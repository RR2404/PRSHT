package moe.prashast.repository;

import moe.prashast.entity.RoleModulePermission;
import moe.prashast.entity.RoleModulePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleModulePermissionRepository extends JpaRepository<RoleModulePermission, RoleModulePermissionId> {

    List<RoleModulePermission> findByRoleIdAndIsActive(Short roleId, Short isActive);


    boolean existsByRoleIdAndModuleIdAndIsActive(
            Integer roleId,
            Short moduleId,
            Short isActive
    );
}
