package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.request.pojo.DisabilityReportReq;
import moe.prashast.request.pojo.SchoolDashboardSummaryReq;
import moe.prashast.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;


    @PostMapping("/get-student-disability-report")
    public ResponseEntity<?> getStudentDisabilityReport(@Valid @RequestBody DisabilityReportReq request) {
       try {

           Object jsonResponse = reportService.getStudentDisabilityReport(request);

           if (jsonResponse != null) {
               return ResponseEntity.ok(new Response(Messages.SUCCESS, jsonResponse));
           } else {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(false, HttpStatus.NOT_FOUND.value(),
                       Messages.NO_DATA_FOUND, null));
           }
       }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @PostMapping("/get-report/school-dashboard-summary")
    public ResponseEntity<?> getReportSchoolDashboardSummary(@Valid @RequestBody SchoolDashboardSummaryReq req){
        try{


            return reportService.getReportSchoolDashboardSummary(req);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }

    }
}
