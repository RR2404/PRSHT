package moe.prashast.service;

import moe.prashast.dto.SchoolMasterLiveDto;

import java.util.List;

public interface SchoolMasterLiveService {

    SchoolMasterLiveDto fetchAllBySchoolId(Integer schoolId);

}

