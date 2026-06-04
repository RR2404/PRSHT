package moe.prashast.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import moe.prashast.bean.RegionHierarchyRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.request.pojo.RegionHierarchyRequest;
import moe.prashast.service.RegionHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/region")
public class RegionHierarchyController {

    @Autowired
    private RegionHierarchyService regionHierarchyService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/fetch")
    public ResponseEntity<?> getRegionDetails(@Valid @RequestBody RegionHierarchyRequestBean request) {

        String jsonResponse = regionHierarchyService.getRegionDetails(request);

        if (jsonResponse != null) {

            try {

                Object data = objectMapper.readValue(jsonResponse, Object.class);

                return ResponseEntity.ok(new Response(Messages.SUCCESS, data) );

            } catch (Exception e) {

                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(false,
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),   Messages.SERVER_ERROR,null));
            }

        } else {

            return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND));
        }
    }

    @PostMapping("/fetch-region-list")
    public ResponseEntity<?> fetchRegionDetails(@Valid @RequestBody RegionHierarchyRequest request) {
        try {

           return regionHierarchyService.fetchRegionDetails(request);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @PostMapping("/fetch-user-list")
    public ResponseEntity<?> fetchUserList(@Valid @RequestBody RegionHierarchyRequest req ){
        try {
            return regionHierarchyService.fetchUserList(req);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));        }
    }

}