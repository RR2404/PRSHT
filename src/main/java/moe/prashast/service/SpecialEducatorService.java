package moe.prashast.service;

import moe.prashast.dto.SpecialEducatorDetailsDto;
import moe.prashast.dto.SpecialEducatorDto;
import moe.prashast.dto.SpecialEducatorSpecialistDto;
import moe.prashast.entity.SpecialEducatorSpecialist;
import moe.prashast.request.pojo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecialEducatorService {


    SpecialEducatorDto saveSpecialEducator(SpecialEducatorReq req);

    SpecialEducatorDto getBySpecialEducatorAndSchoolId(Long specialEducatorId, Integer schoolId, Short yearId);

    List<SpecialEducatorDetailsDto> getSpecialEducatorBySchoolIdAndYearId(SpecialEducatorSchIdAndYearIdReq req);

    SpecialEducatorDetailsDto getSpecialEducatorBySEId(Integer specialEducatorId);

    ResponseEntity<?> getSpecialEducatorSectionAssignemt(SpecialEducatorSectionAssignmentReq req);

    ResponseEntity<?> updateSeSectionAssignment(SeSectionAssignRequest request);

    ResponseEntity<?> getSeSectionAssignmentBySeId(String seId);

    SpecialEducatorSpecialist saveSpecialEducatorSpecialist(SpecialEducatorSpecialistReq req);

    SpecialEducatorSpecialistDto getSpecialEducatorSpecialist(SESpecialistMobileReq req);

    ResponseEntity<?> removeSpecialEducatorSpecialist(SESpecialistMobileReq req);

    ResponseEntity<?> UpdateSpeciaEducatorToNewSchool(SpecialEducatorUpdateReq req);

}
