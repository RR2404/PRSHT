package moe.prashast.repository;

import moe.prashast.entity.LoginSessionMgmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginSessionMgmtRepo extends JpaRepository <LoginSessionMgmt, Long> {
    
    LoginSessionMgmt findByUdiseCodeAndRoleId(String udiseCode, Short role);

    Optional<LoginSessionMgmt> findByUdiseCodeAndRoleIdAndPhoneMobile(String udiseCode, Short role, String mobile);

    Optional<LoginSessionMgmt> findByPhoneMobileAndRoleId(String mobile, Short role);

    Optional<LoginSessionMgmt> findByUserIdAndRoleId(Long userId, Short role);

//    Long getLoginId();
}
