package moe.prashast.repository;

import moe.prashast.entity.UserRoleId;
import moe.prashast.entity.UserRoleMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, UserRoleId> {

    // List<UserRoleMap> findByIdUserId(String userId);
    //
    // List<UserRoleMap> findByIdRoleId(Short roleId);
    //
    // List<UserRoleMap> findByIdUserIdAndIsActive(String userId, Short isActive);
    //
    // Optional<UserRoleMap> findByIdUserIdAndIdRoleId(String userId, Short roleId);

    boolean existsByUserUserIdAndRoleRoleId(Long userId, Short roleId);

    boolean existsByUserUserIdAndRoleRoleIdAndIsActive(
            Long userId,
            Short roleId,
            Short isActive);

    Optional<UserRoleMap> findByRoleEntityId(String assignTeacherId);

    Optional<UserRoleMap> findByRoleEntityIdAndRole_RoleId(String specialEducatorId, int i);

    // @Query("SELECT ur.role.roleId FROM UserRoleMap ur WHERE ur.user.userId =
    // :userId AND ur.isActive = 1" )
    // List<Short> findRoleIdsByUserId(@Param("userId") Long userId);

    List<UserRoleMap> findByUser_UserId(Long userId);

    Optional<UserRoleMap> findByUser_UserIdAndRoleEntityIdAndRole_RoleId(Long userId, String specialEducatorId, int i);

    Optional<UserRoleMap> findByUser_UserIdAndRole_RoleId(Long userId, short i);

    Optional<UserRoleMap> findByUser_PhoneMobileAndRole_RoleId(String mobile, Short role);

    Optional<UserRoleMap> findByUser_EmailIdAndRole_RoleId(String email, Short role);

//    Optional<UserRoleMap> findByUserIdAndRoleId(Long userId, Short role);
}
