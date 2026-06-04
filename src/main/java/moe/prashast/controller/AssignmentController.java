package moe.prashast.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.validation.Valid;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.dto.TeacherSectionAssignmentDto;
import moe.prashast.entity.*;
import moe.prashast.repository.*;
import moe.prashast.request.pojo.*;
import moe.prashast.security.service.CustomUserDetails;
import moe.prashast.service.TeacherAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/headmaster")
public class AssignmentController {

//    @Autowired
//    private TeacherAssignmentService teacherAssignmentService;

    @Autowired
    private TeacherSectionAssignmentRepository teacherSecAssignRepo;

    @Autowired
    private  TeacherAssignmentService teacherAssignmentService;

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleMapRepository roleMapRepository;

    @Autowired
    private TeacherProfileCoreRepository teacherProfileCore;

    @Autowired
    private SchoolMasterLiveCoreRepository schoolMasterLiveCoreRepo;

    @Autowired
    private  UserRoleMapRepository userRoleMapRepository;


    @PostMapping("/fetch-teachers-list")
    public ResponseEntity<?> getTeachers(@Valid @RequestBody TchSchoolYearRequest req) {
        try {
            List<Map<String, Object>> teachersList = teacherAssignmentService.getTeachers(req);
            if(!teachersList.isEmpty()){
                return ResponseEntity.ok( new Response(Messages.SUCCESS,teachersList));
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NO_DATA_FOUND));
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }

    }

    @PostMapping("/fetch/teacher-section/fetch-assigned-teacher-list")
    public ResponseEntity<?> getTeacherSecAssignment(@Valid @RequestBody TchSchoolYearRequest req) {

        try {

            List<TeacherSectionAssignment> assignmentList =teacherSecAssignRepo.findBySchoolIdAndYearId(req.getSchoolId(), req.getYearId());

            if (!assignmentList.isEmpty()) {

                return ResponseEntity.ok(  new Response(Messages.SUCCESS, assignmentList));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NO_DATA_FOUND));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    @PostMapping("/save-update/teacher-section/assignment")
    public ResponseEntity<?> updateTeacherSecAssignment(@Valid @RequestBody TchSectionAssignRequest request) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
            }
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            Long userId = user.getUserId();
            short role = user.getRole();

            String userRoleEntity;
            Optional<UserRoleMap> urmOpt = userRoleMapRepository.findByUser_UserIdAndRole_RoleId(userId, role);

            if (urmOpt.isPresent()) {
                UserRoleMap urm = urmOpt.get();
                userRoleEntity = urm.getRoleEntityId().toString();
            } else {
                userRoleEntity = null;
            }


            List<TeacherSectionAssignment> toupdate =new ArrayList<>();

            for(TchSectionAssignRequest.AssignmentData data: request.getAssignments()) {
                Optional<TeacherSectionAssignment> optionalAssignment = teacherSecAssignRepo.findBySchoolIdAndClassIdAndSectionIdAndYearId(
                        request.getSchoolId(), data.getClassId(), data.getSectionId(), request.getYearId());

                if (optionalAssignment.isPresent()) {
                    TeacherSectionAssignment assignment = optionalAssignment.get();
                    assignment.setAssignTeacherId(data.getAssignTeacherId());
                    assignment.setAssignTeacherName(data.getAssignTeacherName());
                    assignment.setAssignEndDate(LocalDate.parse(data.getDeadline()));
                    assignment.setAssignStatus((short) 1);
                    if (userRoleEntity != null && !userRoleEntity.trim().isEmpty())
                        assignment.setModifiedBy(userRoleEntity);
                    assignment.setModifiedTime(LocalDateTime.now());
                    toupdate.add(assignment);

                    createTeacherUserIfNotExists(request, data);
                }
            }

            if(!toupdate.isEmpty()) {
                teacherSecAssignRepo.saveAll(toupdate);
                return ResponseEntity.ok(new Response(Messages.ASSIGN_SUCCESS));
            }

            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NO_DATA_FOUND));
            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    @PostMapping("/fetch/assigned-class/by/teacher-id")
    public ResponseEntity<?> getAssignedClass(@Valid @RequestBody ClassTeacherYearRequest request){

        try {

            List<TeacherSectionAssignment> assignedList =teacherSecAssignRepo.findByAssignTeacherIdAndYearId(request.getTeacherId(), request.getYearId());

            if (!assignedList.isEmpty()) {

                List<TeacherSectionAssignmentDto> dtoList = assignedList.stream().map(TeacherSectionAssignmentDto::convertToDto).toList();

                return ResponseEntity.ok(new Response(Messages.SUCCESS, dtoList));
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NO_DATA_FOUND));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    @PostMapping("/submit/class-section/completed")
    private ResponseEntity<?> submitClassSectionCompleted(@Valid @RequestBody ClassSectionSubmitReq req){
        try{
                return teacherAssignmentService.submitAssignment(req);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    private void createTeacherUserIfNotExists(TchSectionAssignRequest request, TchSectionAssignRequest.AssignmentData data) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userSession = (CustomUserDetails) authentication.getPrincipal();

        TeacherProfileCore tchProfileCore = teacherProfileCore.findByIdEmpStaffId(data.getAssignTeacherId());
        Optional<User> userpresent = userRepository.findByPhoneMobileAndSchoolId(tchProfileCore.getMobile(),request.getSchoolId());

        if(userpresent.isPresent()){
            User user = userpresent.get();
//            List<UserRoleMap> usrRole=userpresent.get().getUserRole();
//            Optional<UserRoleMap> usrRole =roleMapRepository.findByRoleEntityIdAndRole_RoleId(data.getAssignTeacherId(),2);

            Optional<UserRoleMap> usrRole =roleMapRepository.findByUser_UserIdAndRoleEntityIdAndRole_RoleId(user.getUserId(),data.getAssignTeacherId(),2);

            if(usrRole.isEmpty()) {
                RoleMaster roleMaster =roleMasterRepository.findById((short) 2).orElse(null);

                UserRoleMap userRoleMap = new UserRoleMap();
                userRoleMap.setUser(user);
                userRoleMap.setRoleEntityId(Long.valueOf(data.getAssignTeacherId()));
                userRoleMap.setRole(roleMaster);
                userRoleMap.setIsActive((short) 1);
                userRoleMap.setCreatedTime(LocalDateTime.now());
                userRoleMap.setCreatedBy(String.valueOf(user.getUserId()));
//                userRoleMap.setModifiedTime(LocalDateTime.now());
                roleMapRepository.save(userRoleMap);
            }




//            if(usrRole ==null) {
//
//                RoleMaster roleMaster =roleMasterRepository.findById((short) 2).orElse(null);
//
//                UserRoleMap userRoleMap = new UserRoleMap();
//                userRoleMap.setUser(user);
//                userRoleMap.setRoleEntityId(Long.valueOf(data.getAssignTeacherId()));
//                userRoleMap.setRole(roleMaster);
//                userRoleMap.setIsActive((short) 1);
//                userRoleMap.setCreatedTime(LocalDateTime.now());
//                userRoleMap.setModifiedTime(LocalDateTime.now());
//                roleMapRepository.save(userRoleMap);
//            }else{
//                boolean isRole3Present = usrRole.stream().anyMatch(role -> role.getRole().getRoleId() == 2);
//              if(!isRole3Present) {
//                  RoleMaster roleMaster = roleMasterRepository.findById((short) 2).orElse(null);
//                  UserRoleMap userRoleMap = new UserRoleMap();
//                  userRoleMap.setUser(user);
//                  userRoleMap.setRoleEntityId(Long.valueOf(data.getAssignTeacherId()));
//                  userRoleMap.setRole(roleMaster);
//                  userRoleMap.setIsActive((short) 1);
//                  userRoleMap.setCreatedTime(LocalDateTime.now());
//                  userRoleMap.setModifiedTime(LocalDateTime.now());
//                  roleMapRepository.save(userRoleMap);
//              }
//            }
        }

        else {

            // Create User
            User user = new User();
            user.setSchoolId(request.getSchoolId());
            user.setName(data.getAssignTeacherName());
            user.setEmailId(tchProfileCore.getEmail());
            user.setUdiseSchCode(userSession.getUdiseCode());
            user.setPhoneMobile(tchProfileCore.getMobile());
            user.setUserPassword("");
            user.setIsActive((short) 1);
            user.setIsApproved((short) 1);
            user.setCreatedTime(LocalDateTime.now());
//            user.setModifiedTime(LocalDateTime.now());
            user.setCreatedBy(userSession.getUdiseCode());

            User usersaved= userRepository.save(user);

            RoleMaster roleMaster =roleMasterRepository.findById((short) 2).orElse(null);

            // Create Role Mapping
            UserRoleMap userRoleMap = new UserRoleMap();
            userRoleMap.setUser(usersaved);
            userRoleMap.setRoleEntityId(Long.valueOf(data.getAssignTeacherId()));
            userRoleMap.setRole(roleMaster);
            userRoleMap.setIsActive((short) 1);
            userRoleMap.setCreatedTime(LocalDateTime.now());
            userRoleMap.setModifiedTime(LocalDateTime.now());

            roleMapRepository.save(userRoleMap);
        }




//        if (existingUser.isEmpty()) {
//
//            User user = new User();
//            user.setSchoolId(request.getSchoolId());
//            user.setName(data.getAssignTeacherName());
//            user.setEmailId(tchProfileCore.getEmail());
//            user.setUdiseSchCode(userSession.getUdiseCode());
//            user.setPhoneMobile(tchProfileCore.getMobile());
//            user.setUserPassword("");
//            user.setIsActive((short) 1);
//            user.setIsApproved((short) 1);
//            user.setCreatedTime(LocalDateTime.now());
//            user.setModifiedTime(LocalDateTime.now());
//            user.setCreatedBy(userSession.getUdiseCode());
//
//            userRepository.save(user);
//
//            RoleMaster roleMaster = roleMasterRepository.findById(Short.valueOf((short) 2)).orElse(null);
////		if (roleMaster == null) {
////			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.INVALID_ROLE_ID));
////
////		}
//
//            UserRoleMap userRoleMap = new UserRoleMap();
//            userRoleMap.setUser(user);
//            userRoleMap.setRoleEntityId(Long.valueOf(data.getAssignTeacherId()));
//            userRoleMap.setRole(roleMaster);
//            userRoleMap.setIsActive((short) 1);
//            userRoleMap.setCreatedTime(LocalDateTime.now());
//            userRoleMap.setModifiedTime(LocalDateTime.now());
//            roleMapRepository.save(userRoleMap);
//        }

    }


    @PostMapping("/fetch-headmaster-list")
    public ResponseEntity<?> findHeadmasterDetails(@Valid @RequestBody HeadmasterDetailsRequest req){
        try{

             return teacherAssignmentService.findHeadmasterDetailsBySchoolIdYearIdAndStateId(req);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }

    }

}
