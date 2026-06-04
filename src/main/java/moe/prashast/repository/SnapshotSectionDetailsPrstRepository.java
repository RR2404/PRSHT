package moe.prashast.repository;

import moe.prashast.entity.SnapshotSectionDetailsPrst;
import moe.prashast.entity.SnapshotSectionDetailsPrstId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotSectionDetailsPrstRepository
        extends JpaRepository<SnapshotSectionDetailsPrst, SnapshotSectionDetailsPrstId> {
}

