package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.bean.SchoolIdRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.dto.Response;
import moe.prashast.dto.SchoolMasterLiveDto;
import moe.prashast.service.SchoolMasterLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/school-master")
public class SchoolMasterLiveController {

    @Autowired
    SchoolMasterLiveService schoolMasterLiveService;


    @PostMapping("/find-school-details/by-schoolId")
    public ResponseEntity<?> fetchBySchoolId(@Valid @RequestBody SchoolIdRequestBean requestBean) {

        try {
            Integer schoolId = requestBean.getSchoolId();
            SchoolMasterLiveDto data = schoolMasterLiveService.fetchAllBySchoolId(schoolId);

            if(data != null){
                return ResponseEntity.ok(new Response(Messages.SUCCESS, data));
            } else {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }
}
