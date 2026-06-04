package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.constant.Messages;
import moe.prashast.dto.*;
import moe.prashast.entity.SpecialEducatorSpecialist;
import moe.prashast.repository.RoleModulePermissionRepository;
import moe.prashast.request.pojo.*;
import moe.prashast.service.SpecialEducatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/special-educator")
public class SpecialEducatorController {

    @Autowired
    private SpecialEducatorService specialEducatorService;

    @Autowired
    private RoleModulePermissionRepository roleModulePermissionRepository;

    @PostMapping("/save-update")
    public ResponseEntity<?> saveSEConfiguration(@Valid @RequestBody SpecialEducatorReq req) {

        try {
            boolean isNew = req.getSeSchoolConfigurationId() == null;

            Integer[] roleIds = req.getRoleIds();

            boolean roleModuleFlag = false;

            for (Integer roleId : roleIds) {
                roleModuleFlag = roleModulePermissionRepository.existsByRoleIdAndModuleIdAndIsActive(roleId, req.getModuleId(),(short) 1 );

                if (roleModuleFlag) {
                    break;
                }
            }

            if (!roleModuleFlag) {
                return ResponseEntity.ok(new Response(Messages.NOT_AUTHORIZED));
            }

            if (!isNew) {

                SpecialEducatorDto data =specialEducatorService.getBySpecialEducatorAndSchoolId(req.getSpecialEducatorId(),req.getSchoolId(), req.getYearId() );

                if (data == null) {
                    return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND_UPDATE));
                }

                specialEducatorService.saveSpecialEducator(req);

                return ResponseEntity.ok(new Response(Messages.SE_CONFIG));
            }

            else {
                specialEducatorService.saveSpecialEducator(req);
                return ResponseEntity.ok(new Response(Messages.SE_VERIFIED));
            }

        } catch (Exception ex) {

            return ResponseEntity .status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/get-special-educator-school-configuration")
    public ResponseEntity<?> getConfiguration(@Valid @RequestBody SpecialEducatorGetDto req) {
        try {
            if (req.getSpecialEducatorId() == null) {
                return ResponseEntity.ok(new Response(Messages.SE_REQUIRED));
            }

            if (req.getSchoolId() == null) {
                return ResponseEntity.ok(new Response(Messages.SCHOOL_REQUIRED));
            }

            SpecialEducatorDto data = specialEducatorService.getBySpecialEducatorAndSchoolId(req.getSpecialEducatorId(),req.getSchoolId(), req.getYearId() );

            if (data != null) {
                return ResponseEntity.ok(new Response(Messages.SUCCESS, data));
            } else {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/find-by-schoolId-yearId")
    public ResponseEntity<?> getSpecialEducator(@Valid @RequestBody SpecialEducatorSchIdAndYearIdReq req){
        try {
            List<SpecialEducatorDetailsDto> data = specialEducatorService.getSpecialEducatorBySchoolIdAndYearId(req);
            if(data.isEmpty()){
                return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND) );
            }
            else {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("specialEducator", data);
                return ResponseEntity.ok().body(new Response(Messages.SUCCESS,responseData) );
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/find-by-SpecialEducatorId")
    public ResponseEntity<?> getSpecialEducator(@Valid @RequestBody SpecialEducatorIdReq req){
        try{
           SpecialEducatorDetailsDto data= specialEducatorService.getSpecialEducatorBySEId(req.getSpecialEducatorId());
            if(data==null){
                return ResponseEntity.ok().body(new Response(Messages.NO_DATA_FOUND) );
            }
            else {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("specialEducator", data);
                return ResponseEntity.ok().body(new Response(Messages.SUCCESS,responseData) );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/fetch/assigned/special-educator-list")
    public ResponseEntity<?> fetchSESectionAssignment(@Valid @RequestBody SpecialEducatorSectionAssignmentReq req){
        try {
             return specialEducatorService.getSpecialEducatorSectionAssignemt(req);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/update/se-section/assignment")
    public ResponseEntity<?> updateSeSectionAssignment(@Valid @RequestBody SeSectionAssignRequest request) {

        try {
            return specialEducatorService.updateSeSectionAssignment(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

   @GetMapping("/school-list/find-by-seId/{seId}")
    public ResponseEntity<?> findSeSectionAssignmentBySeId(@PathVariable("seId") Integer seId){
        try{
        	System.out.println("find-by-seId-----------"+seId);
            return specialEducatorService.getSeSectionAssignmentBySeId(String.valueOf(seId));
//        	return null;
        	
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }

   }

   @PostMapping("/save-special-educator-specialist")
    public ResponseEntity<?> saveSpecialEducatorSpecialist(@Valid @RequestBody SpecialEducatorSpecialistReq req){
        try{

             specialEducatorService.saveSpecialEducatorSpecialist(req);
            return ResponseEntity.ok(new Response(Messages.SE_SPECIALIST_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
   }

   @PostMapping("/fetch-special-educator-specialist-by-mobile")
    public ResponseEntity<?> getSpecialEducatorSpecialist(@Valid @RequestBody SESpecialistMobileReq req){
        try{
           SpecialEducatorSpecialistDto dto=  specialEducatorService.getSpecialEducatorSpecialist(req);
            if (dto == null) {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

            return ResponseEntity.ok(new Response(Messages.SUCCESS,dto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }

   }

   @PostMapping("/remove-special-educator-specialist")
    public ResponseEntity<?> removeSpecialEducatorSpecialist(@Valid @RequestBody SESpecialistMobileReq req){
        try{
            return   specialEducatorService.removeSpecialEducatorSpecialist(req);
//            if (!isDeleted) {
//                return ResponseEntity.ok(new Response(Messages.NO_DELETE));
//            }
//
//            return ResponseEntity.ok(new Response(Messages.SE_DELETE));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
   }

    @PostMapping("/update-special-educator-to-new-school")
    public ResponseEntity<?> updateSpecialEducatorToNewSch(@Valid @RequestBody SpecialEducatorUpdateReq req) {
        return specialEducatorService.UpdateSpeciaEducatorToNewSchool(req);
    }





}