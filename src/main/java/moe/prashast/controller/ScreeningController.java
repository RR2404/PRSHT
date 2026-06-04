package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.request.pojo.ScreeningBulkRequest;
import moe.prashast.request.pojo.ScreeningStatusRequest;
import moe.prashast.request.pojo.TchSchoolYearRequest;
import moe.prashast.request.pojo.StudentDeleteRequest;
import moe.prashast.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/screening")
public class ScreeningController {

    @Autowired
    private ScreeningService screeningService;

    @PostMapping("/save-student-part1")
    public ResponseEntity<?> saveAndUpdateStudentScreening(@Valid @RequestBody ScreeningBulkRequest request) {
        try {
            return screeningService.saveAndUpdateScreening(request);

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    @PostMapping("/fetch-student-part1/screening-by-schoolId-yearId")
    public ResponseEntity<?> getScreeningRecord(@Valid @RequestBody TchSchoolYearRequest req) {

        try {
            return screeningService.getScreeningBySchoolAndYear(req);

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    // get_teacher_section_screening_status function
    @PostMapping("/get/teacher/class-section-screening/status")
    public ResponseEntity<?> getTeacherSectionScreeningStatus(@Valid @RequestBody ScreeningStatusRequest request) {

        try {
            return screeningService.getScreeningStatus(request);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }

    }

    @PostMapping("/delete-student-part1")
    public ResponseEntity<?> deleteStudent(@Valid @RequestBody StudentDeleteRequest request) {


        return screeningService.deleteStudent(request);
    }

    // get_se_school_screening_status
    @PostMapping("/get-se-school-screening-status")
    public ResponseEntity<?> getSeSchoolScreeningStatus(@Valid @RequestBody ScreeningStatusRequest request){
        try{
            return screeningService.getSeSchoolScreeningStatus(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }
    

}
