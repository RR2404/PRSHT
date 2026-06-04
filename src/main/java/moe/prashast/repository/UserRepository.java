package moe.prashast.repository;

import moe.prashast.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByPhoneMobile(String mobile);
    List<User> findByPhoneMobileAndUdiseSchCode(String mobile,String udiseCode);

//    Optional<User> findByUdiseSchCode(String udiseCode);

    Optional<User> findByUdiseSchCodeAndUserRoleRoleRoleId(String udiseCode, String roleId);


    List<User> findByUdiseSchCode(String udiseCode);

    Optional<User> findByUdiseSchCodeAndPhoneMobile(String udiseCode, String mobile);

    @Query("""
       SELECT u FROM User u
       JOIN u.userRole ur
       WHERE u.udiseSchCode = :code
       AND ur.role.roleId = :role
       AND ur.isActive = 1
       """)
    Optional<User> findByUdiseAndRole(@Param("code") String code,
                                      @Param("role") Short role);



    @Query("""
       SELECT u FROM User u
       JOIN u.userRole ur
       WHERE u.udiseSchCode = :code
       AND ur.role.roleId = :role
       AND ur.isActive = 1
       AND u.phoneMobile = :mobile
       """)
    Optional<User> findByUdiseAndRoleAndMobile(@Param("code") String code,
                                      @Param("role") Short role,@Param("mobile") String mobile);

    boolean existsByUserIdAndSchoolIdAndIsActive(
            Long userId,
            Integer schoolId,
            Short isActive
    );

    @Query("""
       SELECT u FROM User u
       JOIN  userRole ur
       Where
       u.phoneMobile = :mobile
       AND ur.role.roleId = :role
       AND ur.isActive = 1
       """)
    List<User> findByPhoneMobileAndRole(@Param("mobile") String mobile, @Param("role") Short role);

    @Query("""
       SELECT u FROM User u
       JOIN  userRole ur
       Where
       u.phoneMobile = :mobile
       AND ur.role.roleId = :role
       AND ur.isActive = 1
       """)
    List<User> findMultipleUserByPhoneMobileAndRole(@Param("mobile") String mobile, @Param("role") Short role);


    Optional<User> findByPhoneMobileAndSchoolId(String mobile, Integer schoolId);

    List<User> findByParentId(Long parentId);
}
