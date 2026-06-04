package moe.prashast.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.dto.SchoolDashboardSummaryDto;
import moe.prashast.repository.ReportRepository;
import moe.prashast.repository.StudentPart1ScreeningRepository;
import moe.prashast.request.pojo.DisabilityReportReq;
import moe.prashast.request.pojo.SchoolDashboardSummaryReq;
import moe.prashast.service.ReportService;
import moe.prashast.service.SESchoolScreeningStatusProjection;
import moe.prashast.service.SchoolReportDashboardSummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentPart1ScreeningRepository screeningRepository;


    @Override
    public Object getStudentDisabilityReport(DisabilityReportReq request) {
        String jsonString = reportRepository.getStudentDisabilityReport(request);

        try {
            return objectMapper.readValue(jsonString, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

    @Override
    public ResponseEntity<?> getReportSchoolDashboardSummary(SchoolDashboardSummaryReq req) {
        try{

            List<SchoolReportDashboardSummaryProjection> data = screeningRepository.getSchoolReportDashboardSummary(
                    req.getYearId(),
                    req.getUserId(),
                    req.getRoleId(),
                    req.getScreeningPartId(),
                    req.getStateId(),
                    req.getDistrictId(),
                    req.getBlockId(),
                    req.getSchBroadMgmtId(),
                    req.getSchBroadCatId(),
                    req.getSchRuralUrban()

            );

            if (data.isEmpty()) {

                return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));
            }
            List<SchoolDashboardSummaryDto> responseList = new ArrayList<>();

            for (SchoolReportDashboardSummaryProjection row : data) {

                SchoolDashboardSummaryDto dto =new SchoolDashboardSummaryDto();

                dto.setSummary(objectMapper.readValue(row.getSummaryJson(),Object.class));
                dto.setBroadCategory(objectMapper.readValue(row.getBroadCategoryJson(),Object.class));
                dto.setBroadManagement(objectMapper.readValue(row.getBroadManagementJson(),Object.class));
                dto.setRuralUrban(objectMapper.readValue(row.getRuralUrbanJson(),Object.class));
                dto.setHierarchy(objectMapper.readValue(row.getHierarchyJson(),Object.class));
                dto.setExecStatus(objectMapper.readValue(row.getExecStatus(),Object.class));
                if (row.getErrorDescription() != null) {

                    dto.setErrorDescription(objectMapper.readValue(row.getErrorDescription(),Object.class ));
                }

                responseList.add(dto);
            }


            return ResponseEntity.ok().body(new Response(Messages.SUCCESS, responseList));


//            else {
//                return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));
//
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }
}
