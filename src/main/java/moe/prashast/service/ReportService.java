package moe.prashast.service;

import moe.prashast.request.pojo.DisabilityReportReq;
import moe.prashast.request.pojo.SchoolDashboardSummaryReq;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface ReportService {

    Object getStudentDisabilityReport(DisabilityReportReq request);

    ResponseEntity<?> getReportSchoolDashboardSummary(SchoolDashboardSummaryReq req);
}
