package moe.prashast.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import moe.prashast.constant.Messages;
import moe.prashast.dto.*;
import moe.prashast.entity.*;
import moe.prashast.repository.AppModuleMasterRepository;
import moe.prashast.repository.RoleModulePermissionRepository;
import moe.prashast.repository.UserRepository;
import moe.prashast.repository.UserRoleMapRepository;
import moe.prashast.request.pojo.*;
import moe.prashast.security.service.CustomUserDetails;
import moe.prashast.security.util.JwtUtil;
import moe.prashast.service.CaptchaService;
import moe.prashast.service.LoginService;
import moe.prashast.serviceImpl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
// @RequestMapping("/auth")
@Slf4j
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private AppModuleMasterRepository appModuleMasterRepository;

    @Autowired
    private RoleModulePermissionRepository roleModulePermissionRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRoleMapRepository userRoleMapRepository;

    @Autowired
    private moe.prashast.redis.AuthRedisRepository authRedisRepository;


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        return loginService.login(request, httpServletRequest);
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request,
                                          HttpServletRequest httpServletRequest) {

        return loginService.refreshTokenLoginService(request,httpServletRequest);


    }

    @PostMapping("/auth/login-with-mobile")
    public ResponseEntity<?> checkMobile(@Valid @RequestBody MobileRequest request,
                                         HttpServletRequest httpServletRequest) {

        return loginService.loginWithMobile(request,httpServletRequest);

    }

    @PostMapping("/auth/check-mobile")
    public ResponseEntity<?> checkMobileNumber(@Valid @RequestBody CheckMobile request) {
        try {
            Optional<User> mobile = userRepository.findByPhoneMobile(request.getMobile());
            if (mobile.isPresent()) {
                return ResponseEntity.ok(new Response(Messages.SUCCESS));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, HttpStatus.NOT_FOUND.value(), Messages.NO_DATA_FOUND, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @PostMapping("/auth/check-role-mobile")
    public ResponseEntity<?> checkRoleMobileNumber(@Valid @RequestBody CheckMobileRole request) {
        try {
            List<User> mobile = userRepository.findByPhoneMobileAndRole(request.getMobile(), request.getRole());
            if (!mobile.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.SUCCESS));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, HttpStatus.NOT_FOUND.value(), Messages.NO_DATA_FOUND, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @PostMapping("/auth/check-udise-role-mobile")
    public ResponseEntity<?> checkUdiseRoleMobileNumber(@Valid @RequestBody CheckUdiseRoleMobile request) {
        try {
            User user = null;
            user = userRepository
                    .findByUdiseAndRoleAndMobile(request.getUdise(), request.getRoleId(), request.getMobile())
                    .orElse(null);
            if (user != null && user.getPhoneMobile() != null) {
                return ResponseEntity.ok(new Response(Messages.SUCCESS));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, HttpStatus.NOT_FOUND.value(), Messages.NO_DATA_FOUND, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @PostMapping("/get-role-module-permissions")
    public ResponseEntity<?> getRoleModulePermissions(@Valid @RequestBody RoleRequest req) {
        try {

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
//
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Messages.NOT_AUTHENTICATE));
//            }
//            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

//            if(user.getRole()!=req.getRoleId()){
//                return ResponseEntity.ok().body(new Response(Messages.ROLE_MISMATCH));
//            }

            List<RoleModulePermission> permissions = roleModulePermissionRepository.findByRoleIdAndIsActive(req.getRoleId(), (short) 1);

            // Extract module IDs
            List<Short> moduleIds = permissions.stream().map(RoleModulePermission::getModuleId).toList();

            // Fetch module master data
            List<AppModuleMaster> modules = appModuleMasterRepository.findByModuleIdInAndIsActive(moduleIds, (short) 1);

            Map<String, ModulePermissionDto> moduleMap = new HashMap<>();

            for (RoleModulePermission perm : permissions) {

                AppModuleMaster module = modules.stream().filter(m -> m.getModuleId().equals(perm.getModuleId()))
                        .findFirst().orElse(null);

                if (module != null) {

                    ModulePermissionDto permDto = new ModulePermissionDto();
                    permDto.setIs_view(perm.getIsView());
                    permDto.setIs_create(perm.getIsCreate());
                    permDto.setIs_update(perm.getIsUpdate());
                    permDto.setIs_verify(perm.getIsVerify());
                    permDto.setIs_assign(perm.getIsAssign());
                    permDto.setIs_approve(perm.getIsApprove());

                    moduleMap.put(module.getModuleCode(), permDto);
                }
            }
            if (moduleMap.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }

            return ResponseEntity.ok().body(new Response(Messages.SUCCESS, moduleMap));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Response(new ErrorResponse(Messages.ERROR)));
        }

    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest request) {
        try {
            // user-based key for logout lookup
//            authRedisRepository.deleteByUser(request.getUdiseCode(), request.getMobile());

           return loginService.removeExistingLogin(request);
//            return ResponseEntity.ok(new Response(Messages.LOGOUT_SUCCESS));
        } catch (Exception e) {
            log.error("Logout error", e);
            return ResponseEntity.ok(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @PostMapping("/auth/remove-existing-login")
    public ResponseEntity<?> removeExistingLogin(@Valid @RequestBody LogoutRequest request){
        return loginService.removeExistingLogin(request);

    }
}
