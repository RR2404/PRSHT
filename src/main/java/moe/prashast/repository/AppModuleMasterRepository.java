package moe.prashast.repository;

import moe.prashast.entity.AppModuleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppModuleMasterRepository extends JpaRepository<AppModuleMaster,Short> {
    List<AppModuleMaster> findByModuleIdInAndIsActive(List<Short> moduleIds, Short isActive);
}
