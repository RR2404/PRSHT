package moe.prashast.serviceImpl;

import lombok.RequiredArgsConstructor;
import moe.prashast.bean.TeacherSchoolConfigurationBean;
import moe.prashast.dto.TeacherSchoolConfigurationDto;
import moe.prashast.entity.SchoolConfiguration;
import moe.prashast.entity.TeacherSchoolConfiguration;
import moe.prashast.repository.SchoolConfigurationRepository;
import moe.prashast.repository.TeacherSchoolConfigurationRepository;
import moe.prashast.service.TeacherSchoolConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherSchoolConfigurationServiceImpl
        implements TeacherSchoolConfigurationService {

    @Autowired
    private TeacherSchoolConfigurationRepository repository;

    @Autowired
    private SchoolConfigurationRepository schConfRepository;

    @Override
    public TeacherSchoolConfigurationDto getByTeacherIdAndSchoolId(Long teacherId, Integer schoolId, Short yearId) {

        return repository.findByTeacherIdAndSchoolIdAndYearIdAndIsActive(teacherId, schoolId, yearId, (short) 1)
                .map(this::mapToDto) .orElse(null);
    }

    @Override
    public TeacherSchoolConfigurationDto saveOrUpdate(TeacherSchoolConfigurationBean dto) {

        TeacherSchoolConfiguration entity =repository.findByTeacherIdAndSchoolIdAndYearIdAndIsActive(
                                dto.getTeacherId(), dto.getSchoolId(),dto.getYearId(),(short) 1).orElse(new TeacherSchoolConfiguration());

        boolean isNew = entity.getTeacherSchoolConfigurationId() == null;

        Optional<SchoolConfiguration> schoolData = schConfRepository.findByIdSchoolIdAndIdYearId(dto.getSchoolId(), dto.getYearId());

        // ===== Mapping =====

        if(isNew){
            entity.setTeacherId(dto.getTeacherId());
            entity.setSchoolId(dto.getSchoolId());
            entity.setYearId(dto.getYearId());
            schoolData.ifPresent(schoolConfiguration -> entity.setLanguageId(schoolConfiguration.getLanguageId()));
            entity.setModuleId(dto.getModuleId());
            entity.setModuleIds(generateModuleIds(dto.getModuleId()));
            entity.setRoleIds(dto.getRoleIds());
            entity.setTrainingSkillFlags(dto.getTrainingSkillFlags());
            entity.setTrainingSkillCount(dto.getTrainingSkillCount());
            entity.setIsActive((short) 1);
            entity.setCreatedBy(dto.getUserName());
            entity.setCreatedTime(LocalDateTime.now());
        }

        else {
            entity.setModuleId(dto.getModuleId());
            entity.setModuleIds(generateModuleIds(dto.getModuleId()));
            entity.setLanguageId(dto.getLanguageId());
            entity.setModifiedBy(dto.getUserName());
            entity.setModifiedTime(LocalDateTime.now());
        }

        TeacherSchoolConfiguration saved = repository.save(entity);

        return mapToDto(saved);
    }

    private TeacherSchoolConfigurationDto mapToDto(TeacherSchoolConfiguration entity) {

        TeacherSchoolConfigurationDto dto =new TeacherSchoolConfigurationDto();

        dto.setTeacherSchoolConfigurationId(entity.getTeacherSchoolConfigurationId());
        dto.setTeacherId(entity.getTeacherId());
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

        if (moduleId == 8) {
            return new Integer[]{8};
        }
        if (moduleId == 9) {
            return new Integer[]{8,9};
        } else if (moduleId == 10) {
            return new Integer[]{8,9, 10};
        } else if (moduleId == 11) {
            return new Integer[]{8,9, 10, 11};
        }

        return null;
    }
}