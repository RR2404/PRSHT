package moe.prashast.serviceImpl;

import jakarta.transaction.Transactional;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.dto.ScreeningResponse;
import moe.prashast.entity.SnapshotStudentDataPrst;
import moe.prashast.entity.StudentPart1Screening;
import moe.prashast.repository.SeSectionAssignmentRepo;
import moe.prashast.repository.SnapshotStudentDataPrstRepository;
import moe.prashast.repository.StudentPart1ScreeningRepository;
import moe.prashast.repository.StudentPart2ScreeningRepository;
import moe.prashast.request.pojo.ScreeningBulkRequest;

import moe.prashast.request.pojo.ScreeningStatusRequest;
import moe.prashast.request.pojo.StudentDeleteRequest;
import moe.prashast.request.pojo.StudentScreeningData;
import moe.prashast.request.pojo.TchSchoolYearRequest;
import moe.prashast.security.service.CustomUserDetails;
import moe.prashast.service.SESchoolScreeningStatusProjection;
import moe.prashast.service.ScreeningService;

import moe.prashast.service.TeacherSectionScreeningStatusProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ScreeningServiceImpl implements ScreeningService {

    @Autowired
    private SnapshotStudentDataPrstRepository studentDataPrstRepo;

    @Autowired
    private StudentPart1ScreeningRepository screeningRepository;

    @Autowired
    private StudentPart1ScreeningRepository part1ScreeningRepository;
    
    @Autowired
    private StudentPart2ScreeningRepository  part2ScreeningRepository;
    
    @Autowired
    private SeSectionAssignmentRepo  seSectionAssignmentRepo;

    @Override
    @Transactional
    public ResponseEntity<?> saveAndUpdateScreening(ScreeningBulkRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
        }
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        for (StudentScreeningData studentReq : request.getStudents()) {

            StudentPart1Screening screeningObj;

            // UPDATE CASE
            if (studentReq.getScreeningId() != null) {

                screeningObj = screeningRepository.findById(studentReq.getScreeningId()).orElse(null);
                if(screeningObj==null){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Messages.SCREENING_RECORD_NOT_FOUND);

                }
                screeningObj.setModifiedTime(LocalDateTime.now());
                screeningObj.setModifiedBy(user.getUdiseCode());

            } else {
                Optional<StudentPart1Screening> existingRecord =
                        screeningRepository.findByStateIdAndSchoolIdAndYearIdAndClassIdAndSectionIdAndStudentId(
                                        request.getStateId(),request.getSchoolId(), request.getYearId(),request.getClassId(),
                                        request.getSectionId(),studentReq.getStudentId());

                if (existingRecord.isPresent()) {
                    return ResponseEntity.ok(new Response(Messages.STUDENT_ALREADY_PRESENT));

                }

                // INSERT CASE
                screeningObj = new StudentPart1Screening();
                screeningObj.setCreatedTime(LocalDateTime.now());
                screeningObj.setCreatedBy(user.getUdiseCode());
                screeningObj.setStateId(request.getStateId());
                screeningObj.setSchoolId(request.getSchoolId());
                screeningObj.setYearId(request.getYearId());
                screeningObj.setClassId(request.getClassId());
                screeningObj.setSectionId(request.getSectionId());
                screeningObj.setStudentId(studentReq.getStudentId());

                // fetch snapshot data only for new record
                Optional<SnapshotStudentDataPrst> studentData =studentDataPrstRepo.findByIdStudentId(studentReq.getStudentId());

                if (studentData.isPresent()) {
                    screeningObj.setStudentPen(studentData.get().getStudentPen());
                    screeningObj.setStudentName(studentData.get().getStudentName());
                    screeningObj.setMotherName(studentData.get().getMotherName());
                    screeningObj.setFatherName(studentData.get().getFatherName());
                    screeningObj.setGuardianName(studentData.get().getGuardianName());
                    screeningObj.setGender(studentData.get().getGender());
                    screeningObj.setStudentDob(studentData.get().getStudentDob());
                }
            }

            // Common Fields (Insert + Update)
            screeningObj.setScreeningDoneYn(studentReq.getScreeningDoneYn());
            screeningObj.setScreeningNotDoneReason(studentReq.getScreeningNotDoneReason());
            screeningObj.setScreeningQuestionCount(studentReq.getScreeningQuestionCount());
            screeningObj.setNoConcernYn(studentReq.getNoConcernYn());
            screeningObj.setEligiblePart2Yn(studentReq.getEligiblePart2Yn());
            if(studentReq.getScreeningAnswers()==null){
                screeningObj.setScreeningAnswers("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
            }
            else {
                screeningObj.setScreeningAnswers(studentReq.getScreeningAnswers());
            }

            screeningObj.setScreeningOn(LocalDateTime.now());
            screeningObj.setScreeningBy("");
            screeningObj.setClassSectionScreeningDoneYn(request.getClassSectionScreeningDoneYn());
            screeningRepository.save(screeningObj);

            if (studentReq.getNoConcernYn() != null && studentReq.getNoConcernYn() == 2 && studentReq.getEligiblePart2Yn() != null && studentReq.getEligiblePart2Yn() == 1
                    && studentReq.getScreeningAnswers() != null&& studentReq.getScreeningAnswers().contains("1")) {

                // function call process_student_screening_disability
                part1ScreeningRepository.updateStudentPart1ScreeningImpairment(
                        request.getYearId(), Long.valueOf(request.getSchoolId()),1, request.getSchoolId(),  studentReq.getStudentId().longValue());
            }

        }
        
        // update Number of shortlisted employee
        
      Long noOfPart2Shortlisted=  part1ScreeningRepository.getShortListedForPart2(request.getSchoolId(), request.getClassId(),request.getSectionId(),2,request.getYearId(),1);

      if(noOfPart2Shortlisted !=null && noOfPart2Shortlisted >0) {
      seSectionAssignmentRepo.updateShortlistedStudentCount(noOfPart2Shortlisted, request.getSchoolId(), request.getClassId(), request.getSectionId(), request.getYearId());
      }
        return ResponseEntity.ok(new Response(Messages.SCREENING_SAVED));
    }

    @Override
    public ResponseEntity<?> getScreeningBySchoolAndYear(TchSchoolYearRequest req) {
        List<StudentPart1Screening> list =screeningRepository.findBySchoolIdAndYearId(req.getSchoolId(), Integer.valueOf(req.getYearId()));

        if (list.isEmpty()) {
            return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
        }
        List<ScreeningResponse> responseList = list.stream().map(ScreeningResponse:: convertToResponse).toList();


        return ResponseEntity.ok(new Response(Messages.SUCCESS,responseList));
    }

    @Override
    public ResponseEntity<?> getScreeningStatus(ScreeningStatusRequest request) {
        try {

            List<TeacherSectionScreeningStatusProjection> data =screeningRepository.getScreeningStatus(
                            request.getYearId(),
                            request.getUserId(),
                            request.getRoleId(),
                            request.getStateId(),
                            request.getSchoolId()  );

            if(!data.isEmpty()){
                return ResponseEntity.ok().body(new Response(Messages.SUCCESS,data));

            }
            else {
                return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));

            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteStudent(StudentDeleteRequest req) {
        try{
            int deletedCount = screeningRepository.deleteByStudentIdAndSchoolIdAndYearId(
                    req.getStudentId(),req.getSchoolId(), req.getYearId());
            if(deletedCount>0){
             return ResponseEntity.ok().body(new Response(Messages.STUDENT_DELETE));
            }
            else {
                return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));

        }
    }

    @Override
    public ResponseEntity<?> getSeSchoolScreeningStatus(ScreeningStatusRequest request) {
        try{
            List<SESchoolScreeningStatusProjection> data =screeningRepository.getSeSchoolScreeningStatus(
                    request.getYearId(),
                    request.getUserId(),
                    request.getRoleId(),
                    request.getStateId(),
                    request.getSchoolId()  );
            if(!data.isEmpty()){
                return ResponseEntity.ok().body(new Response(Messages.SUCCESS,data));

            }
            else {
                return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));

        }

    }


}
