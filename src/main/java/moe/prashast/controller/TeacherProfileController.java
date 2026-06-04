package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.bean.SchoolIdRequestBean;
import moe.prashast.bean.TeacherIdRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.dto.Response;
import moe.prashast.dto.TeacherProfileDto;
import moe.prashast.service.TeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
public class TeacherProfileController {

    @Autowired
    private TeacherProfileService teacherProfileService;


    @PostMapping("/find-by-schoolId-yearId")
    public ResponseEntity<?> getTeachersBySchoolIdAndYearId(@Valid @RequestBody SchoolIdRequestBean requestBean) {

        try {
            Integer schoolId = requestBean.getSchoolId();
            List<TeacherProfileDto> data = teacherProfileService.getTeachersBySchoolIdAndYearId(requestBean);
            if (!data.isEmpty()){
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("teachers", data);

                return ResponseEntity.ok(new Response(Messages.SUCCESS, responseData));
            }
            else {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

    }

    @PostMapping("/find-by-teacherId")
    public ResponseEntity<?> getTeachersByTeacherId(@Valid @RequestBody TeacherIdRequestBean requestBean) {

        try {
            Long teacherId = requestBean.getTeacherId();
            TeacherProfileDto data = teacherProfileService.getTeachersByTeacherId(teacherId);
            if (data != null){
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("teachers", data);

                return ResponseEntity.ok(new Response(Messages.SUCCESS, responseData));
            }
            else {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

    }


}
