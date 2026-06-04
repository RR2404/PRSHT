package moe.prashast.repository;

import moe.prashast.entity.HierarchyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HierarchyMasterRepository extends JpaRepository<HierarchyMaster, Short> {

//    @Query("""
//            SELECT h
//            FROM HierarchyMaster h LEFT JOIN User u ON h.hierarchyId=u.hierarchyId
//            WHERE h.hierarchyLevel = :hierarchyLevel
//            AND h.parentId = :parentId
//            AND u.userId IS NULL
//            """)
//    List<HierarchyMaster> fetchNewRegion(@Param("hierarchyLevel") Long hierarchyLevel,@Param("parentId") Long parentId);
    List<HierarchyMaster> findByHierarchyLevelAndParentId( Long hierarchyLevel, Long parentId);


    HierarchyMaster findByHierarchyIdAndParentId(Long hierarchyId, Long parentId);
}
