package moe.prashast.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import moe.prashast.bean.SchoolClassSectionRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.dto.*;
import moe.prashast.service.SchoolClassSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school")
@RequiredArgsConstructor
public class SchoolClassSectionController {

    @Autowired
    private SchoolClassSectionService service;

    // ================= SUMMARY =================
    @PostMapping("/class-section-summary")
    public ResponseEntity<?> getSummary(@Valid @RequestBody SchoolClassSectionRequestBean request) {

        try {
            List<SchoolClassSectionSummaryDto> data =service.getSummary(request.getYearId(),request.getUserId(),
                            request.getRoleId(),request.getStateId(),request.getSchoolId() );

            if (data != null && !data.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.SUCCESS, data));
            } else {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity .status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @PostMapping("/class-section-details")
    public ResponseEntity<?> getDetails(@Valid @RequestBody SchoolClassSectionRequestBean request) {

        try {
            List<SchoolClassSectionDetailsDto> data =service.getDetails(request.getYearId(),request.getUserId(),
                            request.getRoleId(),request.getStateId(),request.getSchoolId() );

            if (data != null && !data.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.SUCCESS, data));
            } else {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
