package moe.prashast.service;



import moe.prashast.bean.SchoolConfigurationBean;
import moe.prashast.dto.SchoolConfigurationDto;
import moe.prashast.request.pojo.ModuleIdUpdateRequest;
import moe.prashast.request.pojo.SchoolConfigRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SchoolConfigurationService {

    SchoolConfigurationDto save(SchoolConfigurationBean dto, String userRoleEntity);

    SchoolConfigurationDto update(SchoolConfigurationBean dto, String userRoleEntity);

    SchoolConfigurationDto getBySchoolIdAndYearId(Integer schoolId, Short yearId);
    Object getStepperData(SchoolConfigRequest request);

    ResponseEntity<?> updateModuleId(ModuleIdUpdateRequest req);

    List<TeacherSectionScreeningStatusProjection> getScreeningData(Integer integer, String schoolId, int roleId, Object o, Integer schoolId1);
}

