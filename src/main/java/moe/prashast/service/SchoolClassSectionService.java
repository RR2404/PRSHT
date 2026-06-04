package moe.prashast.service;

import moe.prashast.dto.SchoolClassSectionDetailsDto;
import moe.prashast.dto.SchoolClassSectionSummaryDto;

import java.util.List;

public interface SchoolClassSectionService {

    List<SchoolClassSectionSummaryDto> getSummary(
            Integer yearId,
            String userId,
            Integer roleId,
            Integer stateId,
            Integer schoolId
    );

    List<SchoolClassSectionDetailsDto> getDetails(
            Integer yearId,
            String userId,
            Integer roleId,
            Integer stateId,
            Integer schoolId
    );
}
