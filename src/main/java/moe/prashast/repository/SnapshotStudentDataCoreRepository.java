package moe.prashast.repository;

import moe.prashast.entity.SnapshotStudentDataCore;
import moe.prashast.entity.SnapshotStudentDataCoreId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnapshotStudentDataCoreRepository
        extends JpaRepository<SnapshotStudentDataCore, SnapshotStudentDataCoreId> {

    List<SnapshotStudentDataCore> findByIdSchoolIdAndIdYearId(Integer schoolId, Short yearId);

}

