package moe.prashast.repository;

import moe.prashast.entity.SnapshotStudentDataPrst;
import moe.prashast.entity.SnapshotStudentDataPrstId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SnapshotStudentDataPrstRepository
        extends JpaRepository<SnapshotStudentDataPrst, SnapshotStudentDataPrstId> {

    Optional<SnapshotStudentDataPrst> findByStudentPen(String studentPen);

    List<SnapshotStudentDataPrst> findByIdSchoolIdAndIdYearIdAndClassIdAndSectionId(Integer schoolId, Integer yearId, Integer classId, Integer sectionId);

//    List<SnapshotStudentDataPrst> findByIdSchoolIdAndIdYearId(Integer schoolId, Integer yearId);

    Optional<SnapshotStudentDataPrst> findByIdStudentId(Integer studentId);
}

