package moe.prashast.service;

import moe.prashast.request.pojo.ScreeningBulkRequest;
import moe.prashast.request.pojo.ScreeningStatusRequest;
import moe.prashast.request.pojo.StudentDeleteRequest;
import moe.prashast.request.pojo.TchSchoolYearRequest;
import org.springframework.http.ResponseEntity;

public interface ScreeningService {

    ResponseEntity<?> saveAndUpdateScreening(ScreeningBulkRequest request);

    ResponseEntity<?> getScreeningBySchoolAndYear(TchSchoolYearRequest request);

    ResponseEntity<?> getScreeningStatus(ScreeningStatusRequest request);

    ResponseEntity<?> deleteStudent(StudentDeleteRequest request);


    ResponseEntity<?> getSeSchoolScreeningStatus(ScreeningStatusRequest request);
}
