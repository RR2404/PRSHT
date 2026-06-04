package moe.prashast.service;

import moe.prashast.bean.StudentRequestBean;
import moe.prashast.request.pojo.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    ResponseEntity<?> updateStudentScreeningPart1Impairment(StudentPart1ScreeningImpairmentUpdateReq request);

    ResponseEntity<?> updateStudentScreeningPart2(StudentPart2ScreeningRequest request);

    ResponseEntity<?> findStudentSectionWise(StudentRequestBean req);

    ResponseEntity<?> findStudentDetails(StudentDetailsRequest req);
    
    ResponseEntity<?> getScreeningPart2BySchoolAndYear(TchSchoolYearRequest request);

    ResponseEntity<?> getScreeningPart2BySchoolAndYearAndStudentId(TchSchoolYearStudentIdRequest req);

    ResponseEntity<?> findStudentDetailsBySpecialEducatorId(SchIdYearIdSeIdRequest request);
}
