package moe.prashast.service;

import moe.prashast.bean.TeacherSchoolConfigurationBean;
import moe.prashast.dto.TeacherSchoolConfigurationDto;

public interface TeacherSchoolConfigurationService {

    TeacherSchoolConfigurationDto getByTeacherIdAndSchoolId(Long teacherId, Integer schoolId, Short yearId);

    TeacherSchoolConfigurationDto saveOrUpdate(TeacherSchoolConfigurationBean dto);
}