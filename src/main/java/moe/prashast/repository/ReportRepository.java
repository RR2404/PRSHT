package moe.prashast.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import moe.prashast.request.pojo.DisabilityReportReq;
import moe.prashast.request.pojo.SchoolDashboardSummaryReq;
import moe.prashast.service.SchoolReportDashboardSummaryProjection;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepository  {

    @PersistenceContext
    private EntityManager entityManager;


    public String getStudentDisabilityReport(DisabilityReportReq request) {
        Query query = entityManager.createNativeQuery(
                "SELECT  public.fn_get_student_disability_report(?,?,?,?,?)::text");

        query.setParameter(1, request.getYearId());
        query.setParameter(2, request.getUserId());
        query.setParameter(3, request.getRoleId());
        query.setParameter(4, request.getSchoolId());
        query.setParameter(5, request.getReportId());

        return query.getSingleResult().toString();

    }

}
