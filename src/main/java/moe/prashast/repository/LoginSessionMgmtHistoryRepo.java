package moe.prashast.repository;

import moe.prashast.entity.LoginSessionMgmtHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginSessionMgmtHistoryRepo extends JpaRepository <LoginSessionMgmtHistory, Long> {


    Optional<LoginSessionMgmtHistory> findByLoginIdAndLoginType(Long loginId, short i);
}
