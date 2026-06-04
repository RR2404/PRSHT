package moe.prashast.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import moe.prashast.bean.SchoolConfigurationBean;
import moe.prashast.bean.SchoolIdYearIdRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.entity.*;
import moe.prashast.repository.*;
import moe.prashast.request.pojo.ModuleIdUpdateRequest;
import moe.prashast.security.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import moe.prashast.dto.*;
import moe.prashast.service.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/school-configuration")
@RequiredArgsConstructor
public class SchoolConfigurationController {

    private final SchoolConfigurationService schoolConfigService;
    private final SchoolConfigurationRepository repository;
    private final TeacherProfileCoreRepository coreTeacherRepository;
    private final TeacherProfilePrstRepository prstTeacherRepository;
    private final SnapshotStudentDataCoreRepository coreStudentRepository;
    private final SnapshotStudentDataPrstRepository prstStudentRepository;
    private final SnapshotSectionDetailsCoreRepository coreSectionRepository;
    private final SnapshotSectionDetailsPrstRepository prstSectionRepository;
    private final TeacherSectionAssignmentRepository teacherAssignmentRepo;
    private final UserRoleMapRepository userRoleMapRepository;
    private final UserRepository userRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final SpecialEducatorCoreRepo specialEducatorCoreRepo;
    private final SpecialEducatorPrstRepo specialEducatorPrstRepo;
    private final SeSectionAssignmentRepo seSectionAssignmentRepo;


    // SAVE-UPDATE
    @PostMapping("/school-config-save-update")
    public ResponseEntity<?> saveOrUpdate(@Valid @RequestBody SchoolConfigurationBean requestBean) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
            }
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            Long userId = user.getUserId();
            short role = user.getRole();

//            Long userId = 1L;
            Integer[] roleIds = requestBean.getRoleIds();
            Integer schoolId = requestBean.getSchoolId();
            Short yearId = requestBean.getYearId();
            Integer action = requestBean.getSaveOrUpdate();
            String userRoleEntity;


//                if (userRoleMapRepository.existsByUserUserIdAndRoleRoleIdAndIsActive(userId,role, (short) 1)) {
                    Optional<UserRoleMap> urmOpt = userRoleMapRepository.findByUser_UserIdAndRole_RoleId(userId, role);

                    if (urmOpt.isPresent()) {
                        UserRoleMap urm = urmOpt.get();
                        userRoleEntity = urm.getRoleEntityId().toString();
                    } else {
                        userRoleEntity = null;
                    }
//                } else {
//                    userRoleEntity = null;
//                }



            if (action == null) {
                return ResponseEntity.badRequest().body(new Response(Messages.FLAG_REQUIRED));
            }

            if (action == 1) { // SAVE

                if (requestBean.getModuleId() > 1) {
                    return ResponseEntity.ok(new Response(Messages.MODULE_ID_SAVE_ERROR));
                } else {
                    if (requestBean.getModuleId() == 1) {
                        // for teacher
                        List<TeacherProfileCore> coreTeacherList = coreTeacherRepository.findBySchoolIdAndIdYearId(schoolId, yearId);
                        if (!coreTeacherList.isEmpty()) {
                            List<TeacherProfilePrst> prstTeacherList = coreTeacherList.stream()
                                    .map(core -> {

                                        TeacherProfilePrst prst = new TeacherProfilePrst();

                                        // Composite Key
                                        prst.setEmpStaffId(core.getId().getEmpStaffId());
                                        prst.setYearId(core.getId().getYearId());

                                        // Normal fields
                                        prst.setSchoolId(core.getSchoolId());
                                        prst.setNatTeacherId(core.getNatTeacherId());
                                        prst.setTchName(core.getTchName().trim().toUpperCase());
                                        prst.setGender(core.getGender());
                                        prst.setDob(core.getDob());
                                        prst.setSocialCat(core.getSocialCat());
                                        prst.setQualAcad(core.getQualAcad());
                                        prst.setMobile(core.getMobile());
                                        prst.setEmail(core.getEmail());
                                        prst.setNatureOfAppt(core.getNatureOfAppt());
                                        prst.setTchType(core.getTchType());
                                        prst.setDojService(core.getDojService());
                                        prst.setClassTaught(core.getClassTaught());
                                        prst.setTrainedCwsn(core.getTrainedCwsn());
                                        prst.setTrainedComp(core.getTrainedComp());
                                        prst.setIsAssigned(core.getIsAssigned());

                                        if (userRoleEntity != null && !userRoleEntity.trim().isEmpty())
                                            prst.setCreatedBy(userRoleEntity);
                                         prst.setCreatedTime(LocalDateTime.now());
//                                        prst.setModifiedBy(String.valueOf(user.getUserId()));
//                                        prst.setModifiedTime(core.getModifiedTime());

                                        return prst;
                                    }).toList();

                            prstTeacherRepository.saveAll(prstTeacherList);
                        }

                        // for student
                        List<SnapshotStudentDataCore> coreStudentList = coreStudentRepository.findByIdSchoolIdAndIdYearId(schoolId, yearId);
                        if (!coreStudentList.isEmpty()) {
                            List<SnapshotStudentDataPrst> prstStudentList = coreStudentList.stream()
                                    .map(core -> {

                                        SnapshotStudentDataPrst prst = new SnapshotStudentDataPrst();

                                        // Composite Key
                                        SnapshotStudentDataPrstId id = new SnapshotStudentDataPrstId();
                                        id.setSchoolId(core.getId().getSchoolId());
                                        id.setStudentId(core.getId().getStudentId());
                                        id.setYearId(core.getId().getYearId());
                                        prst.setId(id);

                                        // Normal Fields
                                        prst.setStateId(core.getStateId());
                                        prst.setStudentPen(core.getStudentPen());
                                        prst.setStudentName(core.getStudentName());
                                        prst.setGender(core.getGender());
                                        prst.setStudentDob(core.getStudentDob());
                                        prst.setClassId(core.getClassId());
                                        prst.setSectionId(core.getSectionId());
                                        prst.setAcYearId(core.getAcYearId());
                                        prst.setMotherName(core.getMotherName());
                                        prst.setFatherName(core.getFatherName());
                                        prst.setGuardianName(core.getGuardianName());
                                        prst.setMobileNo1(core.getMobileNo1());
                                        prst.setMobileNo2(core.getMobileNo2());
                                        prst.setEmailId(core.getEmailId());
                                        prst.setSocCatId(core.getSocCatId());
                                        prst.setMinorityId(core.getMinorityId());
                                        prst.setIsBplYn(core.getIsBplYn());
                                        prst.setEwsYn(core.getEwsYn());
                                        prst.setCwsnYn(core.getCwsnYn());
                                        prst.setImpairmentType(core.getImpairmentType());
                                        prst.setImpairmentPercent(core.getImpairmentPercent());
                                        prst.setNatIndYn(core.getNatIndYn());
                                        prst.setAdmnNumber(core.getAdmnNumber());
                                        prst.setStudentStatus(core.getStudentStatus());
                                        if (userRoleEntity != null && !userRoleEntity.trim().isEmpty())
                                            prst.setCreatedBy(userRoleEntity);
                                        prst.setCreatedTime(LocalDateTime.now());
//                                        prst.setModifiedBy(user.getUdiseCode());
//                                        prst.setModifiedTime(LocalDateTime.now());

                                        return prst;
                                    }).toList();

                            prstStudentRepository.saveAll(prstStudentList);
                        }


                        // for Section
                        List<SnapshotSectionDetailsCore> coreSectionList = coreSectionRepository.findByIdSchoolIdAndIdYearId(schoolId, yearId);
                        if (!coreSectionList.isEmpty()) {
                            List<SnapshotSectionDetailsPrst> prstSectionList = coreSectionList.stream()
                                    .map(core -> {

                                        SnapshotSectionDetailsPrst prst = new SnapshotSectionDetailsPrst();

                                        // Composite Key
                                        SnapshotSectionDetailsPrstId id = new SnapshotSectionDetailsPrstId();
                                        id.setSchoolId(core.getId().getSchoolId());
                                        id.setClassId(core.getId().getClassId());
                                        id.setSectionId(core.getId().getSectionId());
                                        id.setYearId(core.getId().getYearId());
                                        prst.setId(id);

                                        // Normal fields
                                        prst.setSectionName(core.getSectionName());
                                        prst.setSectionAlias(core.getSectionAlias());
                                        prst.setEnrTotal(core.getEnrTotal());
                                        prst.setEnrBoys(core.getEnrBoys());
                                        prst.setEnrGirls(core.getEnrGirls());
                                        prst.setEnrTg(core.getEnrTg());
                                        prst.setIsActive(core.getIsActive());
//                                        prst.setModifiedBy(String.valueOf(user.getUserId()));
//                                        prst.setModifiedTime(LocalDateTime.now());
                                        if (userRoleEntity != null && !userRoleEntity.trim().isEmpty())
                                            prst.setCreatedBy(userRoleEntity);
                                        prst.setCreatedTime(LocalDateTime.now());

                                        return prst;
                                    }).toList();
                            prstSectionRepository.saveAll(prstSectionList);


                            boolean exists = teacherAssignmentRepo.existsBySchoolIdAndYearId(schoolId, yearId);
                            Short noOfDays = requestBean.getNoOfDays();

                            if (!exists) {
                                // to save in teacher section assignment
                                List<TeacherSectionAssignment> assignmentList = new ArrayList<>();
                                List<SpecialEducatorSectionAssignment> seAssignmentList = new ArrayList<>();

                                for (SnapshotSectionDetailsCore sectionCore : coreSectionList) {

                                    TeacherSectionAssignment teacherAssignment = new TeacherSectionAssignment();

                                    teacherAssignment.setYearId(sectionCore.getId().getYearId());
                                    teacherAssignment.setSchoolId(sectionCore.getId().getSchoolId());
                                    teacherAssignment.setClassId(sectionCore.getId().getClassId());
                                    teacherAssignment.setSectionId(sectionCore.getId().getSectionId());

                                    teacherAssignment.setSectionName(sectionCore.getSectionName());
                                    teacherAssignment.setSectionAlias(sectionCore.getSectionAlias());
                                    teacherAssignment.setEnrTotal(sectionCore.getEnrTotal());
                                    teacherAssignment.setIsActive(sectionCore.getIsActive());

                                    teacherAssignment.setClassTeacherId("");
                                    teacherAssignment.setClassTeacherName("");
                                    teacherAssignment.setAssignTeacherId("");
                                    teacherAssignment.setAssignTeacherName("");
                                    teacherAssignment.setAssignStatus((short) 0);
                                    teacherAssignment.setAssignStartDate(LocalDate.now());
                                    if (requestBean.getScreeningP1Deadline() !=null ) {
                                    	teacherAssignment.setAssignEndDate(requestBean.getScreeningP1Deadline());
//                                        teacherAssignment.setAssignEndDate(LocalDate.now().plusDays(noOfDays.longValue()));
                                    }
//                                    teacherAssignment.setModifiedTime(LocalDateTime.now());
                                    if (userRoleEntity != null && !userRoleEntity.trim().isEmpty())
                                        teacherAssignment.setCreatedBy(userRoleEntity);
                                    teacherAssignment.setCreatedTime(LocalDateTime.now());

                                    assignmentList.add(teacherAssignment);
                                }
                                teacherAssignmentRepo.saveAll(assignmentList);


                                // to save in Se_Section Assignment
                                List<TeacherSectionScreeningStatusProjection> screeningData = schoolConfigService.getScreeningData(
                                        Integer.valueOf(coreSectionList.get(0).getId().getYearId()), String.valueOf(coreSectionList.get(0).getId().getSchoolId()), 1, null, coreSectionList.get(0).getId().getSchoolId());


                                for (SnapshotSectionDetailsCore sectionCore : coreSectionList) {
                                    SpecialEducatorSectionAssignment seAssignment = new SpecialEducatorSectionAssignment();

                                    seAssignment.setYearId(sectionCore.getId().getYearId());
                                    seAssignment.setSchoolId(sectionCore.getId().getSchoolId());
                                    seAssignment.setClassId(sectionCore.getId().getClassId());
                                    seAssignment.setSectionId(sectionCore.getId().getSectionId());

                                    seAssignment.setSectionName(sectionCore.getSectionName());
                                    seAssignment.setSectionAlias(sectionCore.getSectionAlias());

                                    //call status function

                                    TeacherSectionScreeningStatusProjection data = screeningData.stream()
                                            .filter(x -> x.getClassId().equals((int) sectionCore.getId().getClassId()) &&
                                                    x.getSectionId().equals((int) sectionCore.getId().getSectionId())) .findFirst() .orElse(null);


                                    seAssignment.setEnrTotal(Optional.ofNullable(data.getTotalEnr()).orElse(0));
                                    seAssignment.setSeEnrTotal(Optional.ofNullable(data.getEligiblePart2Count()).orElse(0));
                                    seAssignment.setIsActive(sectionCore.getIsActive());

                                    seAssignment.setSpecialEducatorId("");
                                    seAssignment.setSpecialEducatorName("");
                                    seAssignment.setSpecialEducatorAssignmentId(null);
//                                    seAssignment.setSpec("");
                                    seAssignment.setAssignStatus((short) 0);
                                    seAssignment.setAssignStartDate(LocalDate.now());
                                    // assign end date left

//                                    seAssignment.setModifiedTime(LocalDateTime.now());
                                    if (userRoleEntity != null && !userRoleEntity.trim().isEmpty())
                                        seAssignment.setCreatedBy(userRoleEntity);
                                    seAssignment.setCreatedTime(LocalDateTime.now());
                                    seAssignmentList.add(seAssignment);
                                }
                                seSectionAssignmentRepo.saveAll(seAssignmentList);

                            }


                        }

                        // for special educator
                        List<SpecialEducatorCore> coreSEList= specialEducatorCoreRepo.findByIdSchoolIdAndIdYearId(schoolId,yearId);
                        if(!coreSEList.isEmpty()){

                            List<SpecialEducatorPrst> prstList = new ArrayList<>();
                            for (SpecialEducatorCore core : coreSEList) {
                                SpecialEducatorPrst se = new SpecialEducatorPrst();

                                SpecialEducatorPrstId prstId = new SpecialEducatorPrstId();
                                prstId.setEmpStaffId(core.getId().getEmpStaffId());
                                prstId.setSchoolId(core.getId().getSchoolId());
                                prstId.setYearId(core.getId().getYearId());

                                se.setId(prstId);

                                se.setNatTeacherId(core.getNatTeacherId());
                                se.setTchName(core.getTchName());
                                se.setGender(core.getGender());
                                se.setDob(core.getDob());
                                se.setSocialCat(core.getSocialCat());
                                se.setQualAcad(core.getQualAcad());
                                se.setMobile(core.getMobile());
                                se.setEmail(core.getEmail());
                                se.setNatureOfAppt(core.getNatureOfAppt());
                                se.setTchType(core.getTchType());
                                se.setDojService(core.getDojService());
                                se.setClassTaught(core.getClassTaught());
                                se.setTrainedCwsn(core.getTrainedCwsn());
                                se.setTrainedComp(core.getTrainedComp());
                                se.setIsAssigned(core.getIsAssigned());
//                                se.setModifiedBy(String.valueOf(user.getUserId()));
//                                se.setModifiedTime(core.getModifiedTime());
                                if (userRoleEntity != null && !userRoleEntity.trim().isEmpty())
                                    se.setCreatedBy(userRoleEntity);
                                se.setCreatedTime(LocalDateTime.now());

                                prstList.add(se);
                            }
                            specialEducatorPrstRepo.saveAll(prstList);

                        }

                    }
                    Optional<SchoolConfiguration> existing = repository.findByIdSchoolIdAndIdYearId(schoolId, yearId);

                    if (existing.isPresent()) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(Messages.SCHOOL_CONFIGURATION_PRESENT));
                    }
                    // User Role Map Entry
                    for (Integer roleIdInt : roleIds) {

                        Short roleId = roleIdInt.shortValue();
                        boolean userFlag = userRepository.existsByUserIdAndSchoolIdAndIsActive(userId, schoolId, (short) 1);
                        if(userFlag){
                            if (userRoleMapRepository.existsByUserUserIdAndRoleRoleIdAndIsActive(userId, roleId, (short) 1)) {
                                continue;
                            }
                            UserRoleMap entity = new UserRoleMap();
                            entity.setUser(userRepository.getReferenceById(userId));
                            entity.setRole(roleMasterRepository.getReferenceById(roleId));
//                            teacherOpt.ifPresent(teacherProfilePrst -> entity.setRoleEntityId(teacherProfilePrst.getEmpStaffId()));
                            entity.setIsActive((short) 1);

                            userRoleMapRepository.save(entity);
                        }
                    }
                    schoolConfigService.save(requestBean,userRoleEntity);

                    return ResponseEntity.ok(new Response(Messages.CONFIG_VERIFIED));



                }

            } else if (action == 2) { // UPDATE
                SchoolConfigurationDto data = schoolConfigService.getBySchoolIdAndYearId(schoolId, yearId);
                if(data != null && data.getModuleIds() != null && requestBean.getModuleId() < data.getModuleId() ){
                    return ResponseEntity.ok(new Response(Messages.MODULE_ID_UPDATE_ERROR_TWO, String.valueOf(requestBean.getModuleId())));
                }
                if (requestBean.getModuleId() == 1) {
                    return ResponseEntity.ok(new Response(Messages.MODULE_ID_UPDATE_ERROR));
                } else {

                    Optional<SchoolConfiguration> existing = repository.findByIdSchoolIdAndIdYearId(schoolId, yearId);

                   // end date update in teacher section assign table
                    List<TeacherSectionAssignment> assignments =teacherAssignmentRepo.findBySchoolIdAndYearId(schoolId, yearId);
                    LocalDate endDate = LocalDate.now().plusDays(requestBean.getNoOfDays());
//                    assignments.forEach(t -> t.setAssignEndDate(endDate));
                    assignments.forEach(t -> t.setAssignEndDate(requestBean.getScreeningP1Deadline()));
                    teacherAssignmentRepo.saveAll(assignments);

                    if (!existing.isPresent()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)  .body(new Response(Messages.SCHOOL_CONFIGURATION_NOT_PRESENT));
                    }

                    // User Role Map Entry
                    for (Integer roleIdInt : roleIds) {

                        Short roleId = roleIdInt.shortValue();
                        boolean userFlag = userRepository.existsByUserIdAndSchoolIdAndIsActive(userId, schoolId, (short) 1);
                        if(userFlag){
                            if (userRoleMapRepository.existsByUserUserIdAndRoleRoleIdAndIsActive(userId, roleId, (short) 1)) {
                                continue;
                            }
                            UserRoleMap entity = new UserRoleMap();
                            entity.setUser(userRepository.getReferenceById(userId));
                            entity.setRole(roleMasterRepository.getReferenceById(roleId));
                            entity.setRoleEntityId(requestBean.getDesignatedTeacherId());
                            entity.setCreatedBy(String.valueOf(user.getUserId()));
                            entity.setCreatedTime(LocalDateTime.now());
//                            entity.setModifiedBy(String.valueOf(user.getUserId()));
//                            entity.setModifiedTime(LocalDateTime.now());
                            entity.setIsActive((short) 1);

                            userRoleMapRepository.save(entity);
                        }
                    }
                    schoolConfigService.update(requestBean,userRoleEntity);

                    return ResponseEntity.ok(new Response(Messages.UPDATED_SUCCESSFULLY));
                }

            } else {
                return ResponseEntity.badRequest().body(new Response(Messages.FLAG_INVALID));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    // Get Data By SchoolId and YearId
    @PostMapping("/school-config-find-by-schoolId-yearId")
    public ResponseEntity<?> getBySchoolIdYearId(@Valid @RequestBody SchoolIdYearIdRequestBean requestBean) {
        try {
            SchoolConfigurationDto data = schoolConfigService.getBySchoolIdAndYearId(requestBean.getSchoolId(), requestBean.getYearId());

            if (data != null) {
                return ResponseEntity.ok(new Response(Messages.SUCCESS, data));
            } else {
                return ResponseEntity.ok(new Response(Messages.CONFIG_NOT_START));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity .status(HttpStatus.INTERNAL_SERVER_ERROR) .body(ex.getMessage());
        }
    }


    @PostMapping("update/moduleId-by-schoolId-yearId")
    public ResponseEntity<?> updateModuleId(@Valid @RequestBody ModuleIdUpdateRequest req) {
        return schoolConfigService.updateModuleId(req);
    }


}
