package moe.prashast.service;

import java.time.LocalDate;
import java.util.*;

import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.HeadmasterDetailsDto;
import moe.prashast.dto.Response;
import moe.prashast.entity.*;
import moe.prashast.repository.*;
import moe.prashast.request.pojo.ClassSectionSubmitReq;
import moe.prashast.request.pojo.HeadmasterDetailsRequest;
import moe.prashast.request.pojo.TchSchoolYearRequest;
import moe.prashast.security.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TeacherAssignmentService {

	@Autowired
	private TeacherProfilePrstRepository repository;
	@Autowired
	private TeacherSectionAssignmentRepository teacherSecAssignRepo;

	@Autowired
	private SeSectionAssignmentRepo seSectionAssignmentRepo;

	@Autowired
	private StudentPart1ScreeningRepository screeningRepository;

	@Autowired
	private SchoolMasterLiveCoreRepository schoolMasterLiveCoreRepo;

	@Autowired
	private TeacherProfilePrstRepository teacherProfilePrstRepository;

	public List<Map<String, Object>> getTeachers(TchSchoolYearRequest request) {

		List<TeacherProfilePrst> teachers = repository.findBySchoolIdAndYearIdAndIsAssigned(request.getSchoolId(),
				request.getYearId(), 0);

		List<Map<String, Object>> response = new ArrayList<>();

		for (TeacherProfilePrst t : teachers) {

			Map<String, Object> map = new HashMap<>();
			map.put("empStaffId", t.getEmpStaffId());
			map.put("tchName", t.getTchName());

			response.add(map);
		}

		return response;
	}

	public ResponseEntity<?> submitAssignment(ClassSectionSubmitReq req) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
			}
			CustomUserDetails userSession = (CustomUserDetails) authentication.getPrincipal();

			List<TeacherSectionAssignment> updatedList = new ArrayList<>();

			for (ClassSectionSubmitReq.AssignmentData data : req.getAssignments()) {

				Optional<TeacherSectionAssignment> tchAssignData = teacherSecAssignRepo
						.findBySchoolIdAndClassIdAndSectionIdAndAssignTeacherId(
								req.getSchoolId(), data.getClassId(), data.getSectionId(), data.getAssignTeacherId());

				if (tchAssignData.isPresent()) {

					TeacherSectionAssignment assignment = tchAssignData.get();

					if (req.getSubmittedRoleId() != 1) {
						assignment.setAssignCompletedYn(data.getAssignCompletedYn());
						assignment.setAssignCompletedBy(data.getAssignCompletedBy());
						assignment.setAssignCompletedTime(LocalDate.now());
					} else {
						assignment.setHmReviewedYn((short) 1);
						assignment.setHmReviewedBy(String.valueOf(userSession.getUserId()));
						assignment.setHmReviewedTime(LocalDate.now());
					}

					updatedList.add(assignment);

					// Save SE initial data per record
					// createInitialSeAssignment(req, data, userSession);
				}
			}

			if (!updatedList.isEmpty()) {
				teacherSecAssignRepo.saveAll(updatedList);
				return ResponseEntity.ok(new Response(Messages.DATA_SAVE));
			} else {
				return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
		}
	}

	public ResponseEntity<?> findHeadmasterDetailsBySchoolIdYearIdAndStateId(HeadmasterDetailsRequest req) {

		try {
			// List<TeacherProfilePrst> teacherData =
			// teacherProfilePrstRepository.findBySchoolIdAndYearIdAndTchNameAndMobile(req.getSchoolId(),req.getYearId(),req.getName(),req.getMobile());

			String teacherName = req.getName().trim().toUpperCase();

			List<TeacherProfilePrst> teacherData = teacherProfilePrstRepository
					.findBySchoolIdAndYearIdAndTchNameOrSchoolIdAndYearIdAndMobile(
							req.getSchoolId(),
							req.getYearId(),
							teacherName,
							req.getSchoolId(),
							req.getYearId(),
							req.getMobile());

			if (teacherData.isEmpty()) {
				return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
			}

			List<HeadmasterDetailsDto> dtoList = teacherData.stream().map(TeacherAssignmentService::mapToDto).toList();
			;

			return ResponseEntity.ok(new Response(Messages.SUCCESS, dtoList));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(Messages.ERROR));
		}
	}

	public static HeadmasterDetailsDto mapToDto(TeacherProfilePrst entity) {

		HeadmasterDetailsDto dto = new HeadmasterDetailsDto();

		dto.setSchoolId(entity.getSchoolId());
		dto.setYearId(entity.getYearId());
		dto.setTeacherName(entity.getTchName());
		dto.setTeacherId(entity.getEmpStaffId());
		dto.setDob(entity.getDob());
		dto.setGender(entity.getGender());
		dto.setEmail(entity.getEmail());
		dto.setTchType(entity.getTchType());
		dto.setTrainedComp(entity.getTrainedComp());

		return dto;
	}
	// private TeacherSectionScreeningStatusProjection
	// getScreeningData(ClassSectionSubmitReq
	// req,ClassSectionSubmitReq.AssignmentData data) {
	//
	// List<TeacherSectionScreeningStatusProjection> list
	// =screeningRepository.getScreeningStatus(
	// req.getYearId(),data.getAssignTeacherId(),req.getSubmittedRoleId(),null,req.getSchoolId());
	//
	// return list.stream().filter(x -> Objects.equals(x.getClassId(),
	// data.getClassId()) &&
	// Objects.equals(x.getSectionId(),
	// data.getSectionId())).findFirst().orElse(null);
	// }

	// private void createInitialSeAssignment(ClassSectionSubmitReq
	// req,ClassSectionSubmitReq.AssignmentData data,CustomUserDetails userSession)
	// {
	//
	// Optional<SpecialEducatorSectionAssignment> existing
	// =seSectionAssignmentRepo.findBySchoolIdAndClassIdAndSectionIdAndYearId(
	// req.getSchoolId(),data.getClassId(),
	// data.getSectionId(),req.getYearId().shortValue());
	//
	// if (existing.isPresent()) {
	// return;
	// }
	// TeacherSectionScreeningStatusProjection screeningData =getScreeningData(req,
	// data);
	//
	// SpecialEducatorSectionAssignment entity = new
	// SpecialEducatorSectionAssignment();
	//
	// entity.setYearId(req.getYearId().shortValue());
	// entity.setSchoolId(req.getSchoolId());
	//// entity.setClassId(data.getClassId());
	//// entity.setSectionId(data.getSectionId());
	//
	// if (screeningData != null) {
	// entity.setEnrTotal(Optional.ofNullable(screeningData.getTotalEnr()).orElse(0));
	// entity.setSeEnrTotal(Optional.ofNullable(screeningData.getEligiblePart2Count()).orElse(0));
	// }
	//
	// entity.setAssignStatus((short) 0);
	// entity.setIsActive((short) 1);
	//
	// entity.setCreatedBy(String.valueOf(userSession.getUserId()));
	// entity.setCreatedTime(LocalDateTime.now());
	//
	// seSectionAssignmentRepo.save(entity);
	// }

}
