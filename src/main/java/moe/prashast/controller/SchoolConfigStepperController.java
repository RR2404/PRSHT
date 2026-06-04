package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.constant.Messages;
import moe.prashast.dto.Response;
import moe.prashast.request.pojo.SchoolConfigRequest;
import moe.prashast.service.SchoolConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school")
public class SchoolConfigStepperController {

    @Autowired
    private SchoolConfigurationService schoolConfigService;
    
    @PostMapping("/stepper")
    public ResponseEntity<?> getSchoolConfigStepper(@Valid @RequestBody SchoolConfigRequest request){

        Object jsonResponse = schoolConfigService.getStepperData(request);

        if(jsonResponse!=null){
            return ResponseEntity.ok(new Response(Messages.SUCCESS,jsonResponse));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(false,HttpStatus.NOT_FOUND.value(),
                    Messages.NO_DATA_FOUND,null));
        }

    }
}
