package moe.prashast.serviceImpl;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import moe.prashast.bean.SchoolConfigurationBean;

import moe.prashast.constant.Messages;
import moe.prashast.request.pojo.ModuleIdUpdateRequest;
import moe.prashast.security.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import moe.prashast.dto.*;
import moe.prashast.entity.*;
import moe.prashast.repository.*;
import moe.prashast.request.pojo.SchoolConfigRequest;
import moe.prashast.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolConfigurationServiceImpl implements SchoolConfigurationService {

    @Autowired
    private SchoolConfigurationRepository schoolConfigRepository;

    @Autowired
    private SchoolConfigurationHistoryRepository historyRepository;

    @Autowired
    private SchoolStepperRepository stepperRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SchoolMasterLivePrstRepo schoolMasterLivePrstRepo;

    @Autowired
    private TeacherProfilePrstRepository teacherProfilePrstRepository;

    @Autowired
    private StudentPart1ScreeningRepository screeningRepository;


    @Override
  public Object getStepperData(SchoolConfigRequest request) {
        String jsonString = stepperRepo.getSchoolConfigurationStepper(request);

        try {
            return objectMapper.readValue(jsonString, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }

  }

    @Override
    public ResponseEntity<?> updateModuleId(ModuleIdUpdateRequest req) {

        try{

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
            }

            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            Optional<SchoolConfiguration> schoolDetails = schoolConfigRepository.findByIdSchoolIdAndIdYearId(req.getSchoolId(), req.getYearId());

            if(schoolDetails.isEmpty()){
               return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));

           }
               SchoolConfiguration toUpdate = schoolDetails.get();
               if(schoolDetails.get().getModuleId()<req.getModuleId()){
                 toUpdate.setModuleId(req.getModuleId());
               }
               toUpdate.setModuleIds(updateModuleIds(req.getModuleId(),req.getSchoolId(),req.getYearId()));
               toUpdate.setModifiedBy(String.valueOf(user.getUserId()));
               toUpdate.setModifiedTime(LocalDateTime.now());
               schoolConfigRepository.save(toUpdate);
               return ResponseEntity.ok().body(new Response(Messages.MODULE_ID_SUCCESS));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity .status(HttpStatus.INTERNAL_SERVER_ERROR) .body(e.getMessage());
        }

    }



    @Override
    public List<TeacherSectionScreeningStatusProjection> getScreeningData(Integer yearId, String schoolId, int roleId, Object o, Integer schoolId1){

        return screeningRepository.getScreeningStatus( yearId,schoolId,roleId,null,schoolId1);
    }

    @Override
    public SchoolConfigurationDto save(SchoolConfigurationBean bean, String userRoleEntity) {


        SchoolMasterLivePrst prstData= schoolMasterLivePrstRepo.findBySchoolId(bean.getSchoolId());
        SchoolConfiguration entity = new SchoolConfiguration();
        entity.setId(new SchoolConfigurationId(bean.getSchoolId(), bean.getYearId()));
        entity.setNoOfDays(bean.getNoOfDays());
        entity.setLanguageId(bean.getLanguageId());
        entity.setModuleId(bean.getModuleId());
        entity.setModuleIds(generateModuleIds(bean.getModuleId()));
        entity.setIsActive(bean.getIsActive());
        entity.setCreatedBy(userRoleEntity);
        entity.setCreatedTime(LocalDateTime.now());
        entity.setRoleIds(bean.getRoleIds());
//        entity.setScreeningP1Deadline(LocalDate.now().plusDays(30));
//        LocalDate today = LocalDate.now();
//        LocalDate maxDate = today.plusDays(30);
//
//        LocalDate deadline = bean.getScreeningP1Deadline();
//
//        if (deadline.isBefore(today) || deadline.isAfter(maxDate)) {
//            throw new IllegalArgumentException(
//                    "Deadline must be between today and next 30 days"
//            );
//        }

        entity.setScreeningP1Deadline(bean.getScreeningP1Deadline());
        entity.setScreeningP1Deadline(bean.getScreeningP1Deadline());
        if (bean.getRoleIds() != null && Arrays.asList(bean.getRoleIds()).contains(1)) {
            entity.setDataImportedAt(prstData.getDataImported());
        }

        entity.setDesignatedTchId(bean.getDesignatedTeacherId());
        if (bean.getScreeningP1Deadline() != null) {
            entity.setScreeningP2Deadline(LocalDate.now().plusMonths(2));
        }

        return mapToDto(schoolConfigRepository.save(entity));
    }


    @Override
    @Transactional
    public SchoolConfigurationDto update(SchoolConfigurationBean dto, String userRoleEntity) {

        SchoolConfigurationId id =new SchoolConfigurationId(dto.getSchoolId(), dto.getYearId());

        SchoolConfiguration existing = schoolConfigRepository.findById(id).orElse(null);

        // STEP 1: Insert old record into history
        SchoolConfigurationHistory history = new SchoolConfigurationHistory();

        history.setSchoolId(existing.getId().getSchoolId());
        history.setYearId(existing.getId().getYearId());
        history.setNoOfDays(existing.getNoOfDays());
        history.setLanguageId(existing.getLanguageId());
        history.setModuleId(existing.getModuleId());
        history.setModuleIds(existing.getModuleIds());
        history.setIsActive(existing.getIsActive());
        history.setCreatedBy(userRoleEntity);
        history.setCreatedTime(LocalDateTime.now());
//        history.setModifiedBy(existing.getModifiedBy());
//        history.setModifiedTime(existing.getModifiedTime());
        history.setRoleIds(existing.getRoleIds());
        history.setScreeningP1Deadline(existing.getScreeningP1Deadline());
        historyRepository.save(history);

        // STEP 2: Update main table
        existing.setNoOfDays(dto.getNoOfDays());
        existing.setLanguageId(dto.getLanguageId());

        existing.setModuleId(dto.getModuleId());
        existing.setModuleIds(updateModuleIds(dto.getModuleId(),dto.getSchoolId(),dto.getYearId()));
        existing.setIsActive(dto.getIsActive());
        existing.setModifiedBy(userRoleEntity);
        existing.setModifiedTime(LocalDateTime.now());
        existing.setRoleIds(dto.getRoleIds());
        existing.setDesignatedTchId(dto.getDesignatedTeacherId());
        existing.setScreeningP1Deadline(dto.getScreeningP1Deadline());

        return mapToDto(schoolConfigRepository.save(existing));
    }


    @Override
    public SchoolConfigurationDto getBySchoolIdAndYearId(Integer schoolId, Short yearId) {

        return schoolConfigRepository.findByIdSchoolIdAndIdYearId(schoolId, yearId).map(this::mapToDto).orElse(null);
    }


    private SchoolConfigurationDto mapToDto(SchoolConfiguration entity) {
        Optional<TeacherProfilePrst> teacherData = teacherProfilePrstRepository.findByEmpStaffId(entity.getDesignatedTchId());

        SchoolConfigurationDto dto = new SchoolConfigurationDto();
        dto.setSchoolId(entity.getId().getSchoolId());
        dto.setYearId(entity.getId().getYearId());
        dto.setNoOfDays(entity.getNoOfDays());
        dto.setLanguageId(entity.getLanguageId());
        dto.setModuleId(entity.getModuleId());
        dto.setModuleIds(entity.getModuleIds());
        dto.setRoleIds(entity.getRoleIds());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setIsActive(entity.getIsActive());
        dto.setSyncStatus(entity.getSyncStatus());
        dto.setSyncRequestedAt(entity.getSyncRequestedAt());
        dto.setSyncExpectedAt(entity.getSyncExpectedAt());
        dto.setScreeningP1Deadline(entity.getScreeningP1Deadline());
        dto.setDesignatedTeacherId(entity.getDesignatedTchId());
        dto.setDataImportedAt(entity.getDataImportedAt());
        teacherData.ifPresent(teacherProfilePrst -> dto.setTeacherName(teacherProfilePrst.getTchName()));
        return dto;
    }

    private Integer[] generateModuleIds(Short moduleId) {

//        return switch (modId) {
//            case 1 -> new Integer[]{1};
//            case 2 -> new Integer[]{1, 2, 3};
//            case 3 -> new Integer[]{1, 2, 3, 4};
//            case 4 -> new Integer[]{1, 2, 3, 4, 5};
//            default -> new Integer[]{};
//        };


        if (moduleId == null || moduleId <= 0) {
            return null;
        }

        if (moduleId == 2) {
            return new Integer[]{1, 2, 3};
        }

        return java.util.stream.IntStream
                .rangeClosed(1, moduleId)
                .boxed()
                .toArray(Integer[]::new);
    }


    private Integer[] updateModuleIds(Short moduleId, Integer schoolId, Short yearId) {

        Optional<SchoolConfiguration> schoolCongObj = schoolConfigRepository.findByIdSchoolIdAndIdYearId(schoolId, yearId);

        if (schoolCongObj.isEmpty()) {
            return null;
        }
            if (schoolCongObj.get().getModuleId() == null || schoolCongObj.get().getModuleId() <= 0) {
                return null;
            }

        int maxModuleId = java.util.Arrays.stream(schoolCongObj.get().getModuleIds())
                .filter(java.util.Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0);

        if (moduleId <= maxModuleId) {
            return schoolCongObj.get().getModuleIds();
        }

                if (moduleId == 2) {
                    return new Integer[]{1, 2, 3};
                }

                return java.util.stream.IntStream.rangeClosed(1, moduleId).boxed().toArray(Integer[]::new);
            }
}
