package moe.prashast.repository;

import moe.prashast.entity.SnapshotSectionDetailsCore;
import moe.prashast.entity.SnapshotSectionDetailsCoreId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnapshotSectionDetailsCoreRepository
        extends JpaRepository<SnapshotSectionDetailsCore, SnapshotSectionDetailsCoreId> {

    List<SnapshotSectionDetailsCore> findByIdSchoolIdAndIdYearId(Integer schoolId, Short yearId);

}
