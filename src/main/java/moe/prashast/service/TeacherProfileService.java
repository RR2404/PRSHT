package moe.prashast.service;

import moe.prashast.bean.SchoolIdRequestBean;
import moe.prashast.dto.TeacherProfileDto;

import java.util.List;

public interface TeacherProfileService {

    List<TeacherProfileDto> getTeachersBySchoolIdAndYearId(SchoolIdRequestBean request);

    TeacherProfileDto getTeachersByTeacherId(Long teacherId);

}
