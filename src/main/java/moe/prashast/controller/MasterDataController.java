package moe.prashast.controller;

import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;

import moe.prashast.util.MasterDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class MasterDataController {

    @Autowired
    private MasterDataUtil masterDataUtil;

    @GetMapping("/fetch-master-data")
    public ResponseEntity<?> getMasterData() {

        try {

            Map<String, Object> data = new LinkedHashMap<>();

            data.put("language", MasterDataUtil.getLanguage());
            data.put("category", MasterDataUtil.getCategory());
            data.put("schoolType",MasterDataUtil.getSchoolType());
            data.put("management", MasterDataUtil.getManagement());
            data.put("impairment", MasterDataUtil.getImpairment());
            data.put("managementStateWise", MasterDataUtil.getManagementStateWise());
            data.put("teacherType",MasterDataUtil.getTeacherType());

            return ResponseEntity.ok(new Response(Messages.SUCCESS,data));

        } catch (Exception e) {
         e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body( new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @GetMapping("/reason-for-absent")
    public ResponseEntity<?> getReasonForAbsent(){
        try{

            Map<Integer, String> reasonForAbsent = MasterDataUtil.getReasonForAbsent();
            return ResponseEntity.ok(new Response(Messages.SUCCESS,reasonForAbsent));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Messages.ERROR));
        }
    }

}
