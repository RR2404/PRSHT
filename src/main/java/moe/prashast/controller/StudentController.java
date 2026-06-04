package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.bean.StudentRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.dto.SnapshotStudentDataPrstDto;
import moe.prashast.dto.StudentPart1ScreeningDto;
import moe.prashast.entity.SnapshotStudentDataPrst;
import moe.prashast.entity.StudentPart1Screening;
import moe.prashast.repository.SnapshotStudentDataPrstRepository;
import moe.prashast.repository.StudentPart1ScreeningRepository;
import moe.prashast.request.pojo.*;
import moe.prashast.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private SnapshotStudentDataPrstRepository prstStudentRepository;

    @Autowired
    private StudentPart1ScreeningRepository part1ScreeningRepository;

    @Autowired
    private StudentService studentService;

    @PostMapping("/fetch-student-details")
    public ResponseEntity<?> getStudentByClassIdAndSectionId(@Valid @RequestBody StudentRequestBean requestBean){

        try{
            List<SnapshotStudentDataPrst> prstStudentList = prstStudentRepository.findByIdSchoolIdAndIdYearIdAndClassIdAndSectionId(
                    requestBean.getSchoolId(), requestBean.getYearId(), requestBean.getClassId(), requestBean.getSectionId());

            if(!prstStudentList.isEmpty()){
                List<SnapshotStudentDataPrstDto> dtoList = prstStudentList.stream().map(SnapshotStudentDataPrstDto::convertToDto).toList();

                return ResponseEntity.ok(new Response(Messages.SUCCESS,dtoList));

            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NO_DATA_FOUND));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Messages.ERROR));
        }
    }

    @PostMapping("/part1-screening/status")
    public ResponseEntity<?> getPart2ScreeningStatus(@Valid @RequestBody StudentPart1ScreeningReq req) {
        try {

            List<StudentPart1Screening> studentList =part1ScreeningRepository.findBySchoolIdAndYearIdAndClassIdAndSectionId(req.getSchoolId(), req.getYearId(),req.getClassId(),req.getSectionId());

            List<StudentPart1ScreeningDto> dtoList = new ArrayList<>();

            for (StudentPart1Screening s : studentList) {

                if (s.getEligiblePart2Yn() != null && s.getEligiblePart2Yn() == 1) {

                    StudentPart1ScreeningDto dto = new StudentPart1ScreeningDto();

                    dto.setSchoolId(s.getSchoolId());
                    dto.setYearId(s.getYearId());
                    dto.setStudentId(s.getStudentId());
                    dto.setStudentPen(s.getStudentPen());
                    dto.setStudentName(s.getStudentName());
                    dto.setMotherName(s.getMotherName());
                    dto.setFatherName(s.getFatherName());
                    dto.setGuardianName(s.getGuardianName());
                    dto.setClassId(s.getClassId());
                    dto.setSectionId(s.getSectionId());
                    dto.setGender(s.getGender());
                    dto.setStudentDob(s.getStudentDob());
                    dto.setScreeningDoneYn(s.getScreeningDoneYn());
                    dto.setScreeningNotDoneReason(s.getScreeningNotDoneReason());
                    dto.setScreeningQuestionCount(s.getScreeningQuestionCount());
                    dto.setNoConcernYn(s.getNoConcernYn());
                    dto.setEligiblePart2Yn(s.getEligiblePart2Yn());
                    dto.setScreeningBy(s.getScreeningBy());

                    List<Integer> selectedIndexes = new ArrayList<>();

                    String answers = s.getScreeningAnswers();
                    if (answers != null) {
                        for (int i = 0; i < answers.length(); i++) {
                            if (answers.charAt(i) == '1') {
                                selectedIndexes.add(i);
                            }
                        }
                    }
                    dto.setSelectedAnswerIndexes(selectedIndexes);


                    dto.setClassSectionScreeningDoneYn(s.getClassSectionScreeningDoneYn());

                    dtoList.add(dto);
                }
            }

            return ResponseEntity.ok(new Response(Messages.SUCCESS, dtoList));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Messages.ERROR));
        }
    }

    @PostMapping("/part1-screening/update-student-part1-impairment")
    public ResponseEntity<?> updateStudentPart1ScreeningImpairmentUpdate(@Valid @RequestBody StudentPart1ScreeningImpairmentUpdateReq request){
        return studentService.updateStudentScreeningPart1Impairment(request);

    }

    @PostMapping("/update-student-screening-part2")
    public ResponseEntity<?> updateStudentScreeningPart2(@Valid @RequestBody StudentPart2ScreeningRequest request){
        return studentService.updateStudentScreeningPart2(request);

    }

    @PostMapping("/fetch-student-list-section-wise")
    public ResponseEntity<?> findAllSectionStudent(@Valid @RequestBody StudentRequestBean req){
        return studentService.findStudentSectionWise(req);

    }

    @PostMapping("/find-student-details-by-studentId")
    public ResponseEntity<?> findStudentDetails(@Valid @RequestBody StudentDetailsRequest req){
        return studentService.findStudentDetails(req);

    }
    
    @PostMapping("/fetch-student-part2/screening-part2-by-schoolId-yearId")
    public ResponseEntity<?> getScreeningPart2Record(@Valid @RequestBody TchSchoolYearRequest req) {

        try {
            return studentService.getScreeningPart2BySchoolAndYear(req);

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }
    
    @PostMapping("/fetch-student-part2/screening-part2-by-schoolId-yearId-studentId")
    public ResponseEntity<?> getScreeningPart2RecordByStudentId(@Valid @RequestBody TchSchoolYearStudentIdRequest req) {
        try {
            return studentService.getScreeningPart2BySchoolAndYearAndStudentId(req);

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }
    
    @PostMapping("/fetch-student-part2/screening-part2-status")
    public ResponseEntity<?> getScreeningPart2Status(@Valid @RequestBody TchSchoolYearRequest req) {
        try {
            return studentService.getScreeningPart2BySchoolAndYear(req);

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    @PostMapping("/find-part2-student-by-specialEducatorId")
    ResponseEntity<?> findStudentDetails(@RequestBody SchIdYearIdSeIdRequest request){
        return studentService.findStudentDetailsBySpecialEducatorId(request);

    }
    
    
    

}
