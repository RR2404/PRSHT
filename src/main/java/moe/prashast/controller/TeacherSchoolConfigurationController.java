package moe.prashast.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import moe.prashast.bean.TeacherSchoolConfigurationBean;
import moe.prashast.bean.TeacherSchoolRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.dto.Response;
import moe.prashast.dto.TeacherSchoolConfigurationDto;
import moe.prashast.repository.RoleModulePermissionRepository;
import moe.prashast.service.TeacherSchoolConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher-school-config")
@RequiredArgsConstructor
public class TeacherSchoolConfigurationController {

    @Autowired
    private TeacherSchoolConfigurationService service;

    @Autowired
    private RoleModulePermissionRepository roleModulePermissionRepository;

    @PostMapping("/get-teacher-school-configuration")
    public ResponseEntity<?> getConfiguration(@Valid @RequestBody TeacherSchoolRequestBean requestDto) {
        try {
            if (requestDto.getTeacherId() == null) {
                return ResponseEntity.ok(new Response(Messages.TEACHER_REQUIRED));
            }

            if (requestDto.getSchoolId() == null) {
                return ResponseEntity.ok(new Response(Messages.SCHOOL_REQUIRED));
            }

            TeacherSchoolConfigurationDto data = service.getByTeacherIdAndSchoolId(requestDto.getTeacherId(),requestDto.getSchoolId(), requestDto.getYearId());

            if (data != null) {
                return ResponseEntity.ok(new Response(Messages.SUCCESS, data));
            } else {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }
        } catch (Exception ex) {
            return ResponseEntity .status(HttpStatus.INTERNAL_SERVER_ERROR) .body(ex.getMessage());
        }
    }

    @PostMapping("/teacher-config-save-update")
    public  ResponseEntity<?> saveOrUpdate(@Valid  @RequestBody TeacherSchoolConfigurationBean bean) {

        try {
            boolean isNew = bean.getTeacherSchoolConfigurationId() == null;
            Integer[] roleIds = bean.getRoleIds();
                boolean roleModuleFlag= false;
            for (Integer roleId : roleIds) {
                 roleModuleFlag = roleModulePermissionRepository.existsByRoleIdAndModuleIdAndIsActive(roleId, bean.getModuleId(), (short) 1) ;
                if (roleModuleFlag){
                    break;
                }

            }
            if (!roleModuleFlag)
                return ResponseEntity.ok(new Response(Messages.NOT_AUTHORIZED));

            // only for update
            if(!isNew){
                TeacherSchoolConfigurationDto data = service.getByTeacherIdAndSchoolId(bean.getTeacherId(),bean.getSchoolId(), bean.getYearId());
                if (data == null) {
                    return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND_UPDATE));
                }
                service.saveOrUpdate(bean);
                return ResponseEntity.ok(new Response(Messages.TEACHER_CONFIG_SUCCESS));

            }

            service.saveOrUpdate(bean);
            return ResponseEntity.ok(new Response(Messages.TEACHER_VERIFY_SUCCESS));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}