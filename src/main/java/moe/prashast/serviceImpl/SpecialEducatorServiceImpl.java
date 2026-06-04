package moe.prashast.serviceImpl;

import jakarta.transaction.Transactional;
import moe.prashast.constant.Messages;
import moe.prashast.dto.*;
import moe.prashast.entity.*;
import moe.prashast.repository.*;
import moe.prashast.request.pojo.*;
import moe.prashast.security.service.CustomUserDetails;
import moe.prashast.service.SpecialEducatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SpecialEducatorServiceImpl implements SpecialEducatorService {

    @Autowired
    private SESchoolConfigurationRepo SpeciaSeSchoolConfigurationRepo;

    @Autowired
    private SchoolConfigurationRepository schConfRepository;

    @Autowired
    private SpecialEducatorPrstRepo specialEducatorPrstRepo;

    @Autowired
    private SpecialEducatorCoreRepo specialEducatorCoreRepo;

    @Autowired
    private SeSectionAssignmentRepo seSectionAssignmentRepo;

    @Autowired
    private UserRoleMapRepository roleMapRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private SchoolMasterLivePrstRepo schoolMasterLivePrstRepo;

    @Autowired
    private SpecialEducatorSpecialistRepo specialEducatorSpecialistRepo;

    @Autowired
    private TeacherProfileCoreRepository teacherProfileCoreRepository;

    @Override
    public SpecialEducatorDto saveSpecialEducator(SpecialEducatorReq req) {
        SESchoolConfiguration entity = SpeciaSeSchoolConfigurationRepo
                .findBySpecialEducatorIdAndSchoolIdAndYearIdAndIsActive(
                        req.getSpecialEducatorId(), req.getSchoolId(), req.getYearId(), (short) 1)
                .orElse(new SESchoolConfiguration());
        boolean isNew = entity.getSeSchoolConfId() == null;

        Optional<SchoolConfiguration> schoolData = schConfRepository.findByIdSchoolIdAndIdYearId(req.getSchoolId(),
                req.getYearId());

        entity.setSpecialEducatorId(req.getSpecialEducatorId());
        entity.setSchoolId(req.getSchoolId());
        entity.setYearId(req.getYearId());
        if (isNew) {
            schoolData.ifPresent(schoolConfiguration -> entity.setLanguageId(schoolConfiguration.getLanguageId()));
        } else {
            entity.setLanguageId(req.getLanguageId());
        }

        entity.setModuleId(req.getModuleId());
        entity.setModuleIds(generateModuleIds(req.getModuleId()));
        entity.setRoleIds(req.getRoleIds());
        entity.setTrainingSkillFlags(req.getTrainingSkillFlags());
        entity.setTrainingSkillCount(req.getTrainingSkillCount());
        entity.setIsActive((short) 1);

        if (isNew) {
            entity.setCreatedBy(req.getUserName());
            entity.setCreatedTime(LocalDateTime.now());
        } else {
            entity.setModifiedBy(req.getUserName());
            entity.setModifiedTime(LocalDateTime.now());
        }

        SESchoolConfiguration saved = SpeciaSeSchoolConfigurationRepo.save(entity);

        return mapToDto(saved);
    }

    @Override
    public SpecialEducatorDto getBySpecialEducatorAndSchoolId(Long specialEducatorId, Integer schoolId, Short yearId) {
        return SpeciaSeSchoolConfigurationRepo
                .findBySpecialEducatorIdAndSchoolIdAndYearIdAndIsActive(specialEducatorId, schoolId, yearId, (short) 1)
                .map(this::mapToDto).orElse(null);
    }

    @Override
    public List<SpecialEducatorDetailsDto> getSpecialEducatorBySchoolIdAndYearId(SpecialEducatorSchIdAndYearIdReq req) {

        List<SpecialEducatorDetailsDto> result = new ArrayList<>();
        Optional<SchoolConfiguration> schoolConfiguration = schConfRepository
                .findByIdSchoolIdAndIdYearId(req.getSchoolId(), req.getYearId());
        if (schoolConfiguration.isEmpty()) {
            List<SpecialEducatorCore> specialEducator = specialEducatorCoreRepo.findByIdSchoolId(req.getSchoolId());

            for (SpecialEducatorCore entity : specialEducator) {

                if (entity == null) {
                    continue;
                }

                SpecialEducatorDetailsDto dto = new SpecialEducatorDetailsDto();
                dto.setEmpStaffId(entity.getId().getEmpStaffId());
                dto.setSchoolId(entity.getId().getSchoolId());
                dto.setYearId(entity.getId().getYearId());
                dto.setNatTeacherId(entity.getNatTeacherId());
                dto.setTchName(entity.getTchName());
                dto.setGender(entity.getGender());
                dto.setDob(entity.getDob());
                dto.setSocialCat(entity.getSocialCat());
                dto.setQualAcad(entity.getQualAcad());
                dto.setMobile(entity.getMobile());
                dto.setEmail(entity.getEmail());
                dto.setNatureOfAppt(entity.getNatureOfAppt());
                dto.setTchType(entity.getTchType());
                dto.setDojService(entity.getDojService());
                dto.setClassTaught(entity.getClassTaught());
                dto.setTrainedCwsn(entity.getTrainedCwsn());
                dto.setTrainedComp(entity.getTrainedComp());
                dto.setIsAssigned(entity.getIsAssigned());

                result.add(dto);
            }

        } else {

            Integer moduleId = Integer.valueOf(schoolConfiguration.get().getModuleId());

            if (moduleId >= 1) {

                List<SpecialEducatorPrst> specialEducator = specialEducatorPrstRepo.findByIdSchoolId(req.getSchoolId());

                for (SpecialEducatorPrst entity : specialEducator) {

                    if (entity == null) {
                        continue;
                    }

                    SpecialEducatorDetailsDto dto = new SpecialEducatorDetailsDto();

                    dto.setEmpStaffId(entity.getId().getEmpStaffId());
                    dto.setSchoolId(entity.getId().getSchoolId());
                    dto.setYearId(entity.getId().getYearId());
                    dto.setNatTeacherId(entity.getNatTeacherId());
                    dto.setTchName(entity.getTchName());
                    dto.setGender(entity.getGender());
                    dto.setDob(entity.getDob());
                    dto.setSocialCat(entity.getSocialCat());
                    dto.setQualAcad(entity.getQualAcad());
                    dto.setMobile(entity.getMobile());
                    dto.setEmail(entity.getEmail());
                    dto.setNatureOfAppt(entity.getNatureOfAppt());
                    dto.setTchType(entity.getTchType());
                    dto.setDojService(entity.getDojService());
                    dto.setClassTaught(entity.getClassTaught());
                    dto.setTrainedCwsn(entity.getTrainedCwsn());
                    dto.setTrainedComp(entity.getTrainedComp());
                    dto.setIsAssigned(entity.getIsAssigned());

                    result.add(dto);
                }
            }

        }

        return result;

    }

    @Override
    public SpecialEducatorDetailsDto getSpecialEducatorBySEId(Integer specialEducatorId) {

        List<SpecialEducatorPrst> specialList = specialEducatorPrstRepo.findByIdEmpStaffId(specialEducatorId);

        if (specialList.isEmpty()) {
            return null; // or throw exception
        }

        SpecialEducatorPrst entity = specialList.get(0);

        SpecialEducatorDetailsDto dto = new SpecialEducatorDetailsDto();

        dto.setEmpStaffId(entity.getId().getEmpStaffId());
        dto.setSchoolId(entity.getId().getSchoolId());
        dto.setYearId(entity.getId().getYearId());
        dto.setNatTeacherId(entity.getNatTeacherId());
        dto.setTchName(entity.getTchName());
        dto.setGender(entity.getGender());
        dto.setDob(entity.getDob());
        dto.setSocialCat(entity.getSocialCat());
        dto.setQualAcad(entity.getQualAcad());
        dto.setMobile(entity.getMobile());
        dto.setEmail(entity.getEmail());
        dto.setNatureOfAppt(entity.getNatureOfAppt());
        dto.setTchType(entity.getTchType());
        dto.setDojService(entity.getDojService());
        dto.setClassTaught(entity.getClassTaught());
        dto.setTrainedCwsn(entity.getTrainedCwsn());
        dto.setTrainedComp(entity.getTrainedComp());
        dto.setIsAssigned(entity.getIsAssigned());

        return dto;
    }

    @Override
    public ResponseEntity<?> getSpecialEducatorSectionAssignemt(SpecialEducatorSectionAssignmentReq req) {

        List<SpecialEducatorSectionAssignment> assignList = seSectionAssignmentRepo
                .findBySchoolIdAndYearId(req.getSchoolId(), req.getYearId());

        if (assignList == null || assignList.isEmpty()) {
            return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));
        }

        List<SeSectionAssignmentDto> dtoList = new ArrayList<>();

        for (SpecialEducatorSectionAssignment entity : assignList) {

            SeSectionAssignmentDto dto = new SeSectionAssignmentDto();

            dto.setSpecialEducatorId(entity.getSpecialEducatorId());
            dto.setYearId(entity.getYearId());
            dto.setSchoolId(entity.getSchoolId());
            dto.setClassId(Integer.valueOf(entity.getClassId()));
            dto.setSectionId(Integer.valueOf(entity.getSectionId()));
            dto.setSectionName(entity.getSectionName());
            dto.setSectionAlias(entity.getSectionAlias());
            dto.setEnrTotal(entity.getEnrTotal());
            dto.setSeEnrTotal(entity.getSeEnrTotal());
            dto.setIsActive(entity.getIsActive());
            dto.setSpecialEducatorName(entity.getSpecialEducatorName());
            dto.setAssignStatus(entity.getAssignStatus());
            dto.setAssignStartDate(entity.getAssignStartDate());
            dto.setAssignEndDate(entity.getAssignEndDate());

            dtoList.add(dto);
        }

        return ResponseEntity.ok(new Response(Messages.SUCCESS, dtoList));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSeSectionAssignment(SeSectionAssignRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
        }
        CustomUserDetails userSession = (CustomUserDetails) authentication.getPrincipal();
        List<SpecialEducatorSectionAssignment> toUpdate = new ArrayList<>();

        for (SeSectionAssignRequest.AssignmentData data : request.getAssignments()) {

            Optional<SpecialEducatorSectionAssignment> optional = seSectionAssignmentRepo.findBySchoolIdAndClassIdAndSectionIdAndYearId(
                            request.getSchoolId(), data.getClassId(), data.getSectionId(),request.getYearId().shortValue());

            if (optional.isPresent()) {

                Optional<SpecialEducatorPrst> specialEducatorPrst = specialEducatorPrstRepo.findByIdEmpStaffIdAndIdSchoolId(Integer.valueOf(data.getSpecialEducatorId()),
                                request.getSchoolId());

                if (specialEducatorPrst.isPresent()) {
                    SpecialEducatorPrst sePrstEntity = specialEducatorPrst.get();
                    sePrstEntity.setIsAssigned((short) 1);
                    sePrstEntity.setModifiedTime(LocalDateTime.now());
                    specialEducatorPrstRepo.save(sePrstEntity);
                }

                SpecialEducatorSectionAssignment assignment = optional.get();

                assignment.setSpecialEducatorId(data.getSpecialEducatorId());
                assignment.setSpecialEducatorName(data.getSpecialEducatorName());
                assignment.setAssignEndDate(LocalDate.parse(data.getDeadline()));
                assignment.setAssignStatus((short) 1);
                assignment.setModifiedTime(LocalDateTime.now());
                assignment.setModifiedBy(String.valueOf(userSession.getUserId()));
                assignment.setSeStatus(0);
                assignment.setModifiedTime(LocalDateTime.now());

                toUpdate.add(assignment);
                createSpecialEducatorUserIfNotExists(request, data, userSession, request.getSchoolId());
            }
        }

        if (!toUpdate.isEmpty()) {
            seSectionAssignmentRepo.saveAll(toUpdate);
            return ResponseEntity.ok(new Response(Messages.SE_ASSIGN_SUCCESS));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NO_DATA_FOUND));
        }
    }

    @Override
    public ResponseEntity<?> getSeSectionAssignmentBySeId(String seId) {
        try {

            List<SpecialEducatorSectionAssignment> list = seSectionAssignmentRepo.findBySpecialEducatorId(seId);

            if (list == null || list.isEmpty()) {
                return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));
            }

            List<SpecialEducatorDataDto> dtoList = new ArrayList<>();
            Set<Integer> schoolIds = new HashSet<>();
            for (SpecialEducatorSectionAssignment entity : list) {

                schoolIds.add(entity.getSchoolId());
                SchoolMasterLivePrst schoolData = schoolMasterLivePrstRepo.findBySchoolId(entity.getSchoolId());
                Optional<SchoolConfiguration> schConfigData = schConfRepository
                        .findByIdSchoolIdAndIdYearId(entity.getSchoolId(), (short) 12);
                SpecialEducatorDataDto dto = new SpecialEducatorDataDto();

                dto.setSpecialEducatorId(entity.getSpecialEducatorId());
                dto.setSchoolName(schoolData != null ? schoolData.getSchoolName() : null);
                dto.setYearId(entity.getYearId());
                dto.setSchoolId(entity.getSchoolId());
                dto.setClassId(entity.getClassId());
                dto.setSeEnrTotal(entity.getSeEnrTotal() != null ? entity.getSeEnrTotal() : 0);
                dto.setSectionId(entity.getSectionId());
                dto.setSectionName(entity.getSectionName());
                dto.setSectionAlias(entity.getSectionAlias());
                dto.setEnrTotal(entity.getEnrTotal() != null ? entity.getEnrTotal() : 0);
                dto.setIsActive(entity.getIsActive());
                dto.setSpecialEducatorName(entity.getSpecialEducatorName());
                dto.setAssignStatus(entity.getAssignStatus());
                dto.setAssignStartDate(entity.getAssignStartDate());
                dto.setAssignEndDate(entity.getAssignEndDate());
                dto.setSeStatus(entity.getSeStatus());
                schConfigData.ifPresent(schoolConfiguration -> dto
                        .setSchoolScreeningP1Deadline(schoolConfiguration.getScreeningP1Deadline()));
                dtoList.add(dto);
            }

            List<SchoolMasterLivePrst> schoolList = schoolMasterLivePrstRepo.findBySchoolIdInAndYearId(schoolIds,
                    (short) 12);

            int totalSchools = schoolList.size();

            int totalSections = schoolList.stream().filter(s -> s.getTotalScetion() != null)
                    .mapToInt(SchoolMasterLivePrst::getTotalScetion).sum();

            Map<String, Object> result = new HashMap<>();
            result.put("totalSchools", totalSchools);
            result.put("totalSections", totalSections);
            result.put("data", dtoList);

            return ResponseEntity.ok(new Response(Messages.SUCCESS, result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    @Override
    public SpecialEducatorSpecialist saveSpecialEducatorSpecialist(SpecialEducatorSpecialistReq req) {

        SpecialEducatorSpecialist entity = specialEducatorSpecialistRepo.findByMobile(req.getMobile()).orElse(new SpecialEducatorSpecialist());

        boolean isNew = entity.getSpecialistId() == null;
        Long empStaffId = generateEmpStaffId();

        entity.setSpecialistName(req.getSpecialistName());
        entity.setMobile(req.getMobile());
        entity.setEmailId(req.getEmailId());
        entity.setGender(String.valueOf(req.getGender()));
        entity.setDateOfBirth(req.getDateOfBirth());
        entity.setEducatorType(String.valueOf(req.getEducatorType()));
        entity.setQualificationId(req.getQualificationId());
        entity.setExperienceYears(req.getExperienceYears());
        entity.setReferenceNumber(req.getReferenceNumber());
        if(req.getIsActive()!=null &&( req.getIsActive()==1 || req.getIsActive()==0)){
            entity.setIsActive(req.getIsActive());

        }
        entity.setEmpStaffId(empStaffId);

        if (isNew) {
            // entity.setCreatedBy(req.getCreatedBy());
            entity.setCreatedTime(LocalDate.now());
        } else {
            // entity.setModifiedBy(req.getModifiedBy());
            entity.setModifiedTime(LocalDate.now());
        }
        specialEducatorSpecialistRepo.save(entity);

        SpecialEducatorPrstId id = new SpecialEducatorPrstId();
        id.setEmpStaffId(empStaffId);
        id.setSchoolId(req.getSchoolId());
        id.setYearId(req.getYearId());

        SpecialEducatorPrst sePrst = new SpecialEducatorPrst();
        sePrst.setId(id);
        sePrst.setTchName(req.getSpecialistName());
        sePrst.setMobile(req.getMobile());
        sePrst.setEmail(req.getEmailId());
        sePrst.setGender(req.getGender());
        sePrst.setDob(req.getDateOfBirth());
        sePrst.setTchType(req.getEducatorType());
        // qualificationId
        // experience
        // referenceNumber
        // isActive

        specialEducatorPrstRepo.save(sePrst);

        return null;

    }

    @Override
    public SpecialEducatorSpecialistDto getSpecialEducatorSpecialist(SESpecialistMobileReq req) {

        try {
            // Check Specialist first
            Optional<SpecialEducatorSpecialist> specialistoptional = specialEducatorSpecialistRepo
                    .findByMobile(req.getMobile());

            if (specialistoptional.isPresent()) {
                SpecialEducatorSpecialist entity = specialistoptional.get();

                SpecialEducatorSpecialistDto dto = new SpecialEducatorSpecialistDto();
                dto.setSpecialistName(entity.getSpecialistName());
                dto.setMobile(entity.getMobile());
                dto.setEmailId(entity.getEmailId());
                dto.setGender(entity.getGender());
                dto.setDateOfBirth(entity.getDateOfBirth());
                dto.setEducatorType(entity.getEducatorType());
                dto.setQualificationId(entity.getQualificationId());
                dto.setExperienceYears(entity.getExperienceYears());
                dto.setReferenceNumber(entity.getReferenceNumber());
                dto.setIsActive(entity.getIsActive());

                return dto;
            }

            // Check SpecialEducatorCore
            Optional<SpecialEducatorCore> seCoreOptional = specialEducatorCoreRepo.findByMobile(req.getMobile());

            if (seCoreOptional.isPresent()) {
                SpecialEducatorCore entity = seCoreOptional.get();
                SpecialEducatorSpecialistDto dto = new SpecialEducatorSpecialistDto();
                dto.setSpecialistName(entity.getTchName());
                dto.setMobile(entity.getMobile());
                dto.setEmailId(entity.getEmail());
                dto.setGender(String.valueOf(entity.getGender()));
                dto.setDateOfBirth(entity.getDob());
                // dto.setEducatorType(String.valueOf(entity.getTchType()));

                return dto;
            }

            // Check Teacher
            Optional<TeacherProfileCore> teacherOptional = teacherProfileCoreRepository.findByMobile(req.getMobile());

            if (teacherOptional.isPresent()) {
                TeacherProfileCore entity = teacherOptional.get();

                SpecialEducatorSpecialistDto dto = new SpecialEducatorSpecialistDto();
                dto.setSpecialistName(entity.getTchName());
                dto.setMobile(entity.getMobile());
                dto.setEmailId(entity.getEmail());
                dto.setGender(String.valueOf(entity.getGender()));
                dto.setDateOfBirth(entity.getDob());
                // dto.setEducatorType(String.valueOf(entity.getTchType()));

                return dto;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResponseEntity<?> removeSpecialEducatorSpecialist(SESpecialistMobileReq req) {
        try {
            Optional<SpecialEducatorPrst> optional = specialEducatorPrstRepo.findByMobileAndIdSchoolId(req.getMobile(),
                    req.getSchoolId());

            SpecialEducatorPrst entity = optional.get();
            if (entity.getIsAssigned() == null || entity.getIsAssigned() == 0) {
                specialEducatorPrstRepo.delete(entity);
                return ResponseEntity.ok().body(new Response(Messages.SE_DELETE));
            }

            return ResponseEntity.ok().body(new Response(Messages.NO_DELETE));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
    }

    @Override
    public ResponseEntity<?> UpdateSpeciaEducatorToNewSchool(SpecialEducatorUpdateReq req) {
        try {
            Optional<SpecialEducatorPrst> prstData = specialEducatorPrstRepo
                    .findByMobileAndIdSchoolIdAndIdYearId(req.getMobile(), req.getSchoolId(), req.getYearId());

            if (prstData.isPresent()) {
                return ResponseEntity.ok(new Response(Messages.ALREADY_ASSIGNED));
            }

            Optional<SpecialEducatorCore> specialEduObj = specialEducatorCoreRepo.findByMobile(req.getMobile());
            if (specialEduObj.isPresent()) {

                SpecialEducatorPrstId id = new SpecialEducatorPrstId();
                id.setSchoolId(req.getSchoolId());
                id.setYearId(req.getYearId());
                id.setEmpStaffId(specialEduObj.get().getId().getEmpStaffId());

                SpecialEducatorPrst sePrst = new SpecialEducatorPrst();
                sePrst.setId(id);
                sePrst.setNatTeacherId(specialEduObj.get().getNatTeacherId());
                sePrst.setTchName(specialEduObj.get().getTchName());
                sePrst.setGender(specialEduObj.get().getGender());
                sePrst.setDob(specialEduObj.get().getDob());
                sePrst.setSocialCat(specialEduObj.get().getSocialCat());
                sePrst.setQualAcad(specialEduObj.get().getQualAcad());
                sePrst.setMobile(specialEduObj.get().getMobile());
                sePrst.setEmail(specialEduObj.get().getEmail());
                sePrst.setNatureOfAppt(specialEduObj.get().getNatureOfAppt());
                sePrst.setTchType(specialEduObj.get().getTchType());
                sePrst.setDojService(specialEduObj.get().getDojService());
                sePrst.setClassTaught(specialEduObj.get().getClassTaught());
                sePrst.setTrainedCwsn(specialEduObj.get().getTrainedCwsn());
                sePrst.setTrainedComp(specialEduObj.get().getTrainedComp());
                sePrst.setIsAssigned(specialEduObj.get().getIsAssigned());

                specialEducatorPrstRepo.save(sePrst);

                return ResponseEntity.ok(new Response(Messages.SPECIALIST_UPDATE));
            }

            Optional<SpecialEducatorSpecialist> specialistData = specialEducatorSpecialistRepo
                    .findByMobile(req.getMobile());

            if (specialistData.isPresent()) {

                SpecialEducatorPrstId id = new SpecialEducatorPrstId();
                id.setSchoolId(req.getSchoolId());
                id.setYearId(req.getYearId());
                id.setEmpStaffId(specialistData.get().getEmpStaffId());

                SpecialEducatorPrst sePrst = new SpecialEducatorPrst();
                sePrst.setId(id);
                sePrst.setTchName(specialistData.get().getSpecialistName());
                sePrst.setGender(Short.valueOf(specialistData.get().getGender()));
                sePrst.setDob(specialistData.get().getDateOfBirth());
                sePrst.setMobile(specialistData.get().getMobile());
                sePrst.setEmail(specialistData.get().getEmailId());
                specialEducatorPrstRepo.save(sePrst);

                return ResponseEntity.ok(new Response(Messages.SPECIALIST_UPDATE));
            }

            // new changes teacher Core
            Optional<TeacherProfileCore> teacherCore = teacherProfileCoreRepository.findByMobile(req.getMobile());

            if (teacherCore.isPresent()) {

                TeacherProfileCore teacher = teacherCore.get();

                SpecialEducatorPrstId id = new SpecialEducatorPrstId();
                id.setSchoolId(req.getSchoolId());
                id.setYearId(req.getYearId());
                id.setEmpStaffId(teacher.getId().getEmpStaffId());

                SpecialEducatorPrst sePrst = new SpecialEducatorPrst();
                sePrst.setId(id);

                sePrst.setTchName(teacher.getTchName());
                sePrst.setMobile(teacher.getMobile());
                sePrst.setEmail(teacher.getEmail());
                sePrst.setGender(teacher.getGender());
                sePrst.setDob(teacher.getDob());
                sePrst.setSocialCat(teacher.getSocialCat());
                sePrst.setQualAcad(teacher.getQualAcad());
                sePrst.setMobile(teacher.getMobile());
                sePrst.setEmail(teacher.getEmail());
                sePrst.setNatureOfAppt(teacher.getNatureOfAppt());
                sePrst.setTchType(teacher.getTchType());
                sePrst.setDojService(teacher.getDojService());
                sePrst.setClassTaught(teacher.getClassTaught());
                sePrst.setTrainedCwsn(teacher.getTrainedCwsn());
                sePrst.setTrainedComp(teacher.getTrainedComp());
                sePrst.setIsAssigned(teacher.getIsAssigned());

                specialEducatorPrstRepo.save(sePrst);

                return ResponseEntity.ok(new Response(Messages.SPECIALIST_UPDATE));
            }

            return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    private void createSpecialEducatorUserIfNotExists(SeSectionAssignRequest request,
            SeSectionAssignRequest.AssignmentData data, CustomUserDetails userSession, Integer schoolId) {

        Optional<SpecialEducatorPrst> seProfile = specialEducatorPrstRepo
                .findByIdEmpStaffIdAndIdSchoolId(Integer.parseInt(data.getSpecialEducatorId()), schoolId);

        Optional<User> userPresent = userRepository.findByPhoneMobileAndSchoolId(seProfile.get().getMobile(),
                request.getSchoolId());
        if (userPresent.isPresent()) {
            User user = userPresent.get();
            Optional<UserRoleMap> usrRole = roleMapRepository.findByUser_UserIdAndRoleEntityIdAndRole_RoleId(user.getUserId(), data.getSpecialEducatorId(), 3);

            // List<UserRoleMap> usrRole1=userPresent.get().getUserRole();
            // System.out.println("usrRole---"+usrRole1.size());
            // boolean isRole3Present = usrRole.stream().anyMatch(role ->
            // role.getRole().getRoleId() == 3);
            if (usrRole.isEmpty()) {

                RoleMaster roleMaster = roleMasterRepository.findById((short) 3).orElse(null);

                UserRoleMap userRoleMap = new UserRoleMap();
                userRoleMap.setUser(user);
                userRoleMap.setRoleEntityId(Long.valueOf(data.getSpecialEducatorId()));
                userRoleMap.setRole(roleMaster);
                userRoleMap.setIsActive((short) 1);
                userRoleMap.setCreatedTime(LocalDateTime.now());
                userRoleMap.setModifiedTime(LocalDateTime.now());
                roleMapRepository.save(userRoleMap);
            }
        }

        else {

            // Create User
            User user = new User();
            user.setSchoolId(request.getSchoolId());
            user.setName(data.getSpecialEducatorName());
            user.setEmailId(seProfile.get().getEmail());
            user.setUdiseSchCode(userSession.getUdiseCode());
            user.setPhoneMobile(seProfile.get().getMobile());
            user.setUserPassword("");
            user.setIsActive((short) 1);
            user.setIsApproved((short) 1);
            user.setCreatedTime(LocalDateTime.now());
            user.setModifiedTime(LocalDateTime.now());
            user.setCreatedBy(userSession.getUdiseCode());

            User userSaved = userRepository.save(user);
            RoleMaster roleMaster = roleMasterRepository.findById((short) 3).orElse(null);

            // Create Role Mapping
            UserRoleMap userRoleMap = new UserRoleMap();
            userRoleMap.setUser(userSaved);
            userRoleMap.setRoleEntityId(Long.valueOf(data.getSpecialEducatorId()));
            userRoleMap.setRole(roleMaster);
            userRoleMap.setIsActive((short) 1);
            userRoleMap.setCreatedTime(LocalDateTime.now());
            userRoleMap.setModifiedTime(LocalDateTime.now());

            roleMapRepository.save(userRoleMap);
        }
    }

    private SpecialEducatorDto mapToDto(SESchoolConfiguration entity) {

        SpecialEducatorDto dto = new SpecialEducatorDto();

        dto.setSeSchoolConfigurationId(entity.getSeSchoolConfId());
        dto.setSpecialEducatorId(entity.getSpecialEducatorId());
        dto.setSchoolId(entity.getSchoolId());
        dto.setYearId(entity.getYearId());
        dto.setLanguageId(entity.getLanguageId());
        dto.setModuleId(entity.getModuleId());
        dto.setModuleIds(entity.getModuleIds());
        dto.setRoleIds(entity.getRoleIds());
        dto.setTrainingSkillFlags(entity.getTrainingSkillFlags());
        dto.setTrainingSkillCount(entity.getTrainingSkillCount());
        dto.setIsActive(entity.getIsActive());

        return dto;
    }

    private Integer[] generateModuleIds(Short moduleId) {

        if (moduleId == null) {
            return null;
        }

        if (moduleId == 12) {
            return new Integer[] { 12 };
        } else if (moduleId == 13) {
            return new Integer[] { 12, 13 };
        } else if (moduleId == 14) {
            return new Integer[] { 12, 13, 14 };
        } else if (moduleId == 15) {
            return new Integer[] { 12, 13, 14, 15 };
        }

        return null;
    }

    @Transactional
    private Long generateEmpStaffId() {

        Long lastId = specialEducatorPrstRepo.findLastEmpStaffIdStartingFromNine();

        if (lastId == null) {
            return 900000001L; // starting value
        }

        return lastId + 1;
    }
}
