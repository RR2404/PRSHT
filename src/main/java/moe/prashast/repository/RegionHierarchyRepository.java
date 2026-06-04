package moe.prashast.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import moe.prashast.bean.RegionHierarchyRequestBean;
import org.springframework.stereotype.Repository;

@Repository
public class RegionHierarchyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String getRegionDetails(RegionHierarchyRequestBean request) {

        String sql = """
                SELECT hierarchy_details
                FROM public.get_hierarchy_details(?,?,?,?,?)
                """;

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter(1, request.getYearId());
        query.setParameter(2, request.getUserId());
        query.setParameter(3, request.getRoleId());
        query.setParameter(4, request.getRegionType());
        query.setParameter(5, request.getRegionValue());

        Object result = query.getSingleResult();

        return result != null ? result.toString() : null;
    }
}
