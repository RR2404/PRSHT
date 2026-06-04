package moe.prashast.serviceImpl;

import jakarta.transaction.Transactional;
import moe.prashast.bean.StudentRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.dto.ScreeningPart2Response;
import moe.prashast.dto.SpecialEducatorDetailsDto;
import moe.prashast.entity.SpecialEducatorCore;
import moe.prashast.entity.SpecialEducatorSectionAssignment;
import moe.prashast.entity.StudentPart2Screening;
import moe.prashast.repository.SeSectionAssignmentRepo;
import moe.prashast.repository.StudentPart1ScreeningRepository;
import moe.prashast.repository.StudentPart2ScreeningRepository;
import moe.prashast.request.pojo.*;
import moe.prashast.security.service.CustomUserDetails;
import moe.prashast.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentPart1ScreeningRepository part1ScreeningRepository;

    @Autowired
    private StudentPart2ScreeningRepository part2ScreeningRepository;

    @Autowired
    private SeSectionAssignmentRepo seSectionAssignmentRepo;

    @Transactional
    public ResponseEntity<?> updateStudentScreeningPart1Impairment(StudentPart1ScreeningImpairmentUpdateReq req) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
            }

            String optional = part1ScreeningRepository.updateStudentPart1ScreeningImpairment(req.getYearId(),
                    req.getUserId(), req.getRoleId(), req.getSchoolId(), req.getStudentId());

            if (optional.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

            return ResponseEntity.ok(new Response(Messages.UPDATED_SUCCESSFULLY));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateStudentScreeningPart2(StudentPart2ScreeningRequest req) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
            }
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            Optional<StudentPart2Screening> optional = part2ScreeningRepository.findByStudentIdAndSchoolIdAndYearId(
                            req.getStudentId(), req.getSchoolId(), req.getYearId() );

            if (optional.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

            StudentPart2Screening entity = optional.get();

            // -------- PART 1 --------
//            if (req.getScreeningByPart1() != null)
//                entity.setScreeningByPart1(req.getScreeningByPart1());
//
//            if (req.getScreeningOnPart1() != null)
//                entity.setScreeningOnPart1(req.getScreeningOnPart1());

//            if (req.getDisabilityTypeIdsPart1() != null)
//                entity.setDisabilityTypeIdsPart1(req.getDisabilityTypeIdsPart1());

            // -------- SCREENING ANSWERS --------

            if (req.getScreeningAnswersD1() != null) entity.setScreeningAnswersD1(req.getScreeningAnswersD1());
            if (req.getScreeningAnswersD2() != null) entity.setScreeningAnswersD2(req.getScreeningAnswersD2());
            if (req.getScreeningAnswersD3() != null) entity.setScreeningAnswersD3(req.getScreeningAnswersD3());
            if (req.getScreeningAnswersD4() != null) entity.setScreeningAnswersD4(req.getScreeningAnswersD4());
            if (req.getScreeningAnswersD5() != null) entity.setScreeningAnswersD5(req.getScreeningAnswersD5());
            if (req.getScreeningAnswersD6() != null) entity.setScreeningAnswersD6(req.getScreeningAnswersD6());
            if (req.getScreeningAnswersD7() != null) entity.setScreeningAnswersD7(req.getScreeningAnswersD7());
            if (req.getScreeningAnswersD8() != null) entity.setScreeningAnswersD8(req.getScreeningAnswersD8());
            if (req.getScreeningAnswersD9() != null) entity.setScreeningAnswersD9(req.getScreeningAnswersD9());
            if (req.getScreeningAnswersD10() != null) entity.setScreeningAnswersD10(req.getScreeningAnswersD10());
            if (req.getScreeningAnswersD11() != null) entity.setScreeningAnswersD11(req.getScreeningAnswersD11());
            if (req.getScreeningAnswersD12() != null) entity.setScreeningAnswersD12(req.getScreeningAnswersD12());
            if (req.getScreeningAnswersD13() != null) entity.setScreeningAnswersD13(req.getScreeningAnswersD13());
            if (req.getScreeningAnswersD14() != null) entity.setScreeningAnswersD14(req.getScreeningAnswersD14());
            if (req.getScreeningAnswersD15() != null) entity.setScreeningAnswersD15(req.getScreeningAnswersD15());
            if (req.getScreeningAnswersD16() != null) entity.setScreeningAnswersD16(req.getScreeningAnswersD16());
            if (req.getScreeningAnswersD17() != null) entity.setScreeningAnswersD17(req.getScreeningAnswersD17());
            if (req.getScreeningAnswersD18() != null) entity.setScreeningAnswersD18(req.getScreeningAnswersD18());
            if (req.getScreeningAnswersD19() != null) entity.setScreeningAnswersD19(req.getScreeningAnswersD19());
            if (req.getScreeningAnswersD20() != null) entity.setScreeningAnswersD20(req.getScreeningAnswersD20());
            if (req.getScreeningAnswersD21() != null) entity.setScreeningAnswersD21(req.getScreeningAnswersD21());

            // -------- OBSERVATIONS --------

            if (req.getObservationD1() != null) entity.setObservationD1(req.getObservationD1());
            if (req.getObservationD2() != null) entity.setObservationD2(req.getObservationD2());
            if (req.getObservationD3() != null) entity.setObservationD3(req.getObservationD3());
            if (req.getObservationD4() != null) entity.setObservationD4(req.getObservationD4());
            if (req.getObservationD5() != null) entity.setObservationD5(req.getObservationD5());
            if (req.getObservationD6() != null) entity.setObservationD6(req.getObservationD6());
            if (req.getObservationD7() != null) entity.setObservationD7(req.getObservationD7());
            if (req.getObservationD8() != null) entity.setObservationD8(req.getObservationD8());
            if (req.getObservationD9() != null) entity.setObservationD9(req.getObservationD9());
            if (req.getObservationD10() != null) entity.setObservationD10(req.getObservationD10());
            if (req.getObservationD11() != null) entity.setObservationD11(req.getObservationD11());
            if (req.getObservationD12() != null) entity.setObservationD12(req.getObservationD12());
            if (req.getObservationD13() != null) entity.setObservationD13(req.getObservationD13());
            if (req.getObservationD14() != null) entity.setObservationD14(req.getObservationD14());
            if (req.getObservationD15() != null) entity.setObservationD15(req.getObservationD15());
            if (req.getObservationD16() != null) entity.setObservationD16(req.getObservationD16());
            if (req.getObservationD17() != null) entity.setObservationD17(req.getObservationD17());
            if (req.getObservationD18() != null) entity.setObservationD18(req.getObservationD18());
            if (req.getObservationD19() != null) entity.setObservationD19(req.getObservationD19());
            if (req.getObservationD20() != null) entity.setObservationD20(req.getObservationD20());
            if (req.getObservationD21() != null) entity.setObservationD21(req.getObservationD21());

            // -------- PART 2 --------
            if (req.getDisabilityTypeIdsPart2() != null)
                entity.setDisabilityTypeIdsPart2(req.getDisabilityTypeIdsPart2());

            if (req.getDisabilityTypeIds() != null)
                entity.setDisabilityTypeIds(req.getDisabilityTypeIds());

            if (req.getScreeningDoneYn() != null)
                entity.setScreeningDoneYn(req.getScreeningDoneYn());

            entity.setScreeningOn(LocalDateTime.now());
            entity.setScreeningBy(String.valueOf(user.getUserId()));

            entity.setModifiedBy(String.valueOf(user.getUserId()));
            entity.setModifiedTime(LocalDateTime.now());

            part2ScreeningRepository.save(entity);

            //to fetch part2 status


            // to save seStatus


            return ResponseEntity.ok(new Response(Messages.PART2_UPDATE));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @Override
    public ResponseEntity<?> findStudentSectionWise(StudentRequestBean req) {
        try{
            List<StudentPart2Screening> studentData= part2ScreeningRepository.findBySchoolIdAndYearIdAndClassIdAndSectionId(req.getSchoolId(),req.getYearId(),req.getClassId(),req.getSectionId());
            if(studentData.isEmpty()){
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

            return ResponseEntity.ok().body(new Response(Messages.SUCCESS,studentData));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @Override
    public ResponseEntity<?> findStudentDetails(StudentDetailsRequest req) {
        try {
           Optional<StudentPart2Screening> studentDetails= part2ScreeningRepository.findBySchoolIdAndYearIdAndClassIdAndSectionIdAndStudentId(req.getSchoolId(),req.getYearId(),req.getClassId(),req.getSectionId(),req.getStudentId());

            if(studentDetails.isEmpty()){
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

            return ResponseEntity.ok().body(new Response(Messages.SUCCESS,studentDetails));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }
    
    @Override
    public ResponseEntity<?> getScreeningPart2BySchoolAndYear(TchSchoolYearRequest req) {
    	List<Object[]> list =part2ScreeningRepository.findBySchoolIdAndYearId(req.getSchoolId(), Integer.valueOf(req.getYearId()));

        if (list.isEmpty()) {
            return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
        }
//        List<ScreeningPart2Response> responseList = list.stream().map(ScreeningPart2Response:: convertToResponse).toList();
        
        List<ScreeningPart2Response> responseList =list.stream().map(obj -> ScreeningPart2Response.convertToResponse((Object[]) obj)).toList();
        return ResponseEntity.ok(new Response(Messages.SUCCESS,responseList));
    }

    @Override
    public ResponseEntity<?> getScreeningPart2BySchoolAndYearAndStudentId(TchSchoolYearStudentIdRequest req) {
       List<StudentPart2Screening>  studentData= part2ScreeningRepository.findBySchoolIdAndYearIdAndStudentId(req.getSchoolId(), Integer.valueOf(req.getYearId()),req.getStudentId());
        if (studentData.isEmpty()) {
            return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
        }

        List<ScreeningPart2Response> responseList = studentData.stream().map(ScreeningPart2Response::convertToResponse).toList();
        return ResponseEntity.ok(new Response(Messages.SUCCESS,responseList));
    }

    @Override
    public ResponseEntity<?> findStudentDetailsBySpecialEducatorId(SchIdYearIdSeIdRequest req) {

        try {
            List<SpecialEducatorSectionAssignment> assignmentData =seSectionAssignmentRepo.findBySchoolIdAndYearIdAndSpecialEducatorId(
                    req.getSchoolId(), req.getYearId(), req.getSpecialEducatorId());

            if (assignmentData.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

            List<Short> classIds = assignmentData.stream().map(SpecialEducatorSectionAssignment::getClassId).toList();

            List<Short> sectionIds = assignmentData.stream() .map(SpecialEducatorSectionAssignment::getSectionId).toList();

            List<StudentPart2Screening> part2Data =part2ScreeningRepository.findBySchoolIdAndClassIdInAndSectionIdIn( req.getSchoolId(), classIds, sectionIds);

            
            if (part2Data.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

            List<ScreeningPart2Response> dtoList = new ArrayList<>();

            for (StudentPart2Screening c : part2Data) {
                ScreeningPart2Response dto = new ScreeningPart2Response();

                dto.setClassId(c.getClassId());
                dto.setSectionId(c.getSectionId());
                dto.setStudentId(c.getStudentId());
                dto.setGender(c.getGender());
                dto.setStudentName(c.getStudentName());
                dto.setFatherName(c.getFatherName());
                dto.setStudentDob(c.getStudentDob());
                dto.setStudentPen(c.getStudentPen());
                dto.setScreeningDoneYn(c.getScreeningDoneYn());
                dto.setMotherName(c.getMotherName());
                dto.setScreeningDoneYn(c.getScreeningDoneYn());
                dto.setDisabilityTypeIdsPart1(c.getDisabilityTypeIdsPart1());
                dto.setDisabilityTypeIdsPart2(c.getDisabilityTypeIdsPart2());
                dto.setDisabilityTypeIds(c.getDisabilityTypeIds());

                dto.setStateId(c.getStateId());
                dto.setYearId(c.getYearId());

                dtoList.add(dto);
            }

            return ResponseEntity.ok(new Response(Messages.SUCCESS, dtoList));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

}
