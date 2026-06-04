package moe.prashast.serviceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import moe.prashast.config.LoginSessionProperties;
import moe.prashast.constant.Messages;
import moe.prashast.dto.*;
import moe.prashast.entity.*;
import moe.prashast.redis.AuthRedisRepository;
import moe.prashast.repository.*;
import moe.prashast.request.pojo.LogoutRequest;
import moe.prashast.request.pojo.MobileRequest;
import moe.prashast.request.pojo.RefreshTokenRequest;
import moe.prashast.security.util.JwtUtil;
import moe.prashast.service.CaptchaService;
import moe.prashast.service.LoginService;
import moe.prashast.validation.CommonValidation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

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
    private AuthRedisRepository authRedisRepository;

    @Autowired
    private UserRoleMapRepository userRoleMapRepository;

    @Autowired
    private LoginSessionProperties props;

    @Autowired
    private LoginSessionMgmtRepo loginSessionMgmtRepo;

    @Autowired
    private LoginSessionMgmtHistoryRepo loginSessionMgmtHistoryRepo;

//    @Autowired
//    private CommonValidation commonValidation;

    public ResponseEntity<?> login(LoginRequest request, HttpServletRequest httpServletRequest) {

        try {
            LoginSessionMgmt loginSessionMgmt = null;
            User user = null;

//            String validateRole = commonValidation.checkRole(request.getRole());
//            if (validateRole != null) {
//                return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, validateRole)));
//            }

            if (request.getRole() == 1) {
//                String validationMessageUdise = commonValidation.udiseCodeValidation(request.getUdiseCode());
//                if (validationMessageUdise != null ) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, validationMessageUdise)));
//                }

//                String validatePassword = commonValidation.passwordFieldCheck(request.getPassword());
//                if (validatePassword != null) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, validatePassword)));
//                }
//
//                String checkCaptcha = commonValidation.captchaFieldCheck(request.getCaptchaValue());
//                if (checkCaptcha != null) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, checkCaptcha)));
//                }

                if (StringUtils.isBlank(request.getUdiseCode())) {
//                    throw new ValidationException("UDISE Code is required");
                    return ResponseEntity.ok().body(new Response(Messages.UDISE_REQUIRED));
                }

                user = userRepository.findByUdiseAndRole(request.getUdiseCode(), request.getRole()).orElse(null);
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.HM_NOT_REGISTERED));
                }

                boolean hasRole = user.getUserRole().stream().anyMatch(ur -> ur.getRole().getRoleId().equals(request.getRole())
                                && ur.getRole().getIsActive() == 1);

                if (!hasRole) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Messages.ROLE_NOT_ASSIGNED));
                }

            } else if (request.getRole() == 2) {

//                String validationMessageUdise = commonValidation.udiseCodeValidation(request.getUdiseCode());
//                if (validationMessageUdise != null ) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, validationMessageUdise)));
//                }
//
//                String validationMessageMobile = commonValidation.mobileNoValidation(request.getMobile());
//                if (validationMessageMobile != null ) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, validationMessageMobile)));
//                }
//
//                String validatePassword = commonValidation.passwordFieldCheck(request.getPassword());
//                if (validatePassword != null) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, validatePassword)));
//                }
//
//                String checkCaptcha = commonValidation.captchaFieldCheck(request.getCaptchaValue());
//                if (checkCaptcha != null) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, checkCaptcha)));
//                }

                if (StringUtils.isBlank(request.getMobile())) {
                    return ResponseEntity.ok().body(new Response(Messages.MOBILE_REQUIRED));
                }

                if (StringUtils.isBlank(request.getUdiseCode())) {
                    return ResponseEntity.ok().body(new Response(Messages.UDISE_REQUIRED));
                }


                user = userRepository.findByUdiseAndRoleAndMobile(request.getUdiseCode(), request.getRole(), request.getMobile())
                        .orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NOT_REGISTERED));
                }

            } else {

//                String validationMessageMobile = commonValidation.mobileNoValidation(request.getMobile());
//                if (validationMessageMobile != null ) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, validationMessageMobile)));
//                }
//
//                String validatePassword = commonValidation.passwordFieldCheck(request.getPassword());
//                if (validatePassword != null) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, validatePassword)));
//                }
//
//                String checkCaptcha = commonValidation.captchaFieldCheck(request.getCaptchaValue());
//                if (checkCaptcha != null) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION, checkCaptcha)));
//                }

                if (StringUtils.isBlank(request.getMobile())) {
                    return ResponseEntity.ok().body(new Response(Messages.MOBILE_REQUIRED));
                }

                List<User> seUser = userRepository.findByPhoneMobileAndRole(request.getMobile(), request.getRole());

                if(!seUser.isEmpty()){

                    user = seUser.get(0);
                    if (user == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.SE_NOT_REGISTERED));
                    }
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.SE_NOT_REGISTERED));

                }
            }

            if (user.getUserPassword() == null || user.getUserPassword().trim().isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.GENERATE_PASSWORD));
            }

            String username;
            if (request.getRole() == 1) {
                username = "1:" + request.getUdiseCode() + ":-";
            } else if (request.getRole() == 2) {
                username = request.getRole() + ":" + request.getUdiseCode() + ":" + request.getMobile();
            } else {

                username = request.getRole() + ":" + "-:" + request.getMobile();
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

            // VALIDATE USER
            LoginValidationResult result = validateUserLogin(request.getUdiseCode(), request.getMobile(), request.getRole(), user);

            if (result.hasError()) {
                return result.getErrorResponse();
            }

            boolean isValidCaptcha = false;
            if (request.getCaptchaId() != null || request.getCaptchaValue() != null) {
                isValidCaptcha = captchaService.validateCaptcha(request.getCaptchaId(), request.getCaptchaValue());
            } else {
                return ResponseEntity.ok(new Response(Messages.CAPTCHA_REQUIRED));
            }
            if (!isValidCaptcha) {
                return ResponseEntity.ok(new Response(new ErrorResponse(Messages.INVALID_CAPTCHA)));
            }


            List<RoleMaster> roles = user.getUserRole().stream().filter(r -> r.getIsActive() == 1).map(UserRoleMap::getRole).toList();

            Short roleId = request.getRole();

            List<RoleModulePermission> permissions = roleModulePermissionRepository.findByRoleIdAndIsActive(roleId,(short) 1);

            List<Short> moduleIds = permissions.stream().map(RoleModulePermission::getModuleId).toList();

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

            List<UserRoleMap> roleEntityMaps = userRoleMapRepository.findByUser_UserId(user.getUserId());

            List<UserRoleEntity> roleEntityList = new ArrayList<>();

            for (UserRoleMap r : roleEntityMaps) {
                roleEntityList.add(new UserRoleEntity(r.getRole().getRoleId(), r.getRoleEntityId()));
            }

            UserDto userDto = new UserDto(user, roles, moduleMap, roleEntityList);

            TokenResponse accessTokenResponse = jwtUtil.generateAccessToken(username);
            TokenResponse refreshTokenResponse = jwtUtil.generateRefreshToken(username);

            Instant accessTokenInstant = accessTokenResponse.getExpiryTime().toInstant();
            Instant refreshTokenInstant = refreshTokenResponse.getExpiryTime().toInstant();

            LocalDateTime accessTokenExpiryDateTime = LocalDateTime.ofInstant(accessTokenInstant,
                    ZoneId.systemDefault());
            LocalDateTime refreshTokenExpiryDateTime = LocalDateTime.ofInstant(refreshTokenInstant,
                    ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String accessTokenExpiry = accessTokenExpiryDateTime.format(formatter);
            String refreshTokenExpiry = refreshTokenExpiryDateTime.format(formatter);

            LoginResponseDto responseDto = new LoginResponseDto();

            responseDto.setAccessToken(accessTokenResponse.getToken());
            responseDto.setRefreshToken(refreshTokenResponse.getToken());
            responseDto.setAccessTokenExpiry(accessTokenExpiry);
            responseDto.setRefreshTokenExpiry(refreshTokenExpiry);
            responseDto.setTokenType("Bearer");
            responseDto.setUser(userDto);

            authRedisRepository.saveTokens(userDto.getUdiseCode(), userDto.getMobile(), responseDto);

            saveLoginSession(user, request.getRole(), accessTokenResponse, refreshTokenResponse, accessTokenExpiry,
                    refreshTokenExpiry, httpServletRequest);

            return ResponseEntity.ok(new Response(Messages.LOGIN_SUCCESS, responseDto));

        } catch (BadCredentialsException e) {

            log.error("Invalid credentials for mobile: {}", request.getMobile());
            e.printStackTrace();

            return ResponseEntity.ok(new Response(new ErrorResponse(Messages.INVALID_MOBILE_PASSWORD)));

        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            log.error("Mobile not mapped with UDISE: {}", request.getMobile());

            return ResponseEntity.ok(new Response(new ErrorResponse(e)));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Login API exception", e);

            return ResponseEntity.ok(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    private LoginValidationResult validateUserLogin(String udiseCode, String mobile, Short role, User user) {

        LoginSessionMgmt loginSessionMgmt;

        if (role == 1) {
            if (isAlreadyLoggedIn(user)) {
                Map<String, Boolean> existingLoginResponse = new HashMap<>();
                existingLoginResponse.put("sessionExists", true);

                return new LoginValidationResult(ResponseEntity.ok(new Response(Messages.ALREADY_LOGGEDIN, existingLoginResponse)));
            }

            if (props.isDbEnabled()) {
                loginSessionMgmt = loginSessionMgmtRepo.findByUdiseCodeAndRoleId(udiseCode, role);

                if (loginSessionMgmt != null) {
                    Map<String, Boolean> existingLoginResponse = new HashMap<>();
                    existingLoginResponse.put("sessionExists", true);
                    return new LoginValidationResult(ResponseEntity.ok(new Response(Messages.ALREADY_LOGGEDIN, existingLoginResponse)));
                }
            }

        } else if (role == 2) {

            if (isAlreadyLoggedIn(user)) {
                Map<String, Boolean> existingLoginResponse = new HashMap<>();
                existingLoginResponse.put("sessionExists", true);
                return new LoginValidationResult(
                        ResponseEntity.ok(new Response(Messages.ALREADY_LOGGEDIN, existingLoginResponse)));
            }

            if (props.isDbEnabled()) {
                loginSessionMgmt = loginSessionMgmtRepo.findByUdiseCodeAndRoleIdAndPhoneMobile(udiseCode, role, mobile)
                        .orElse(null);

                if (loginSessionMgmt != null) {
                    Map<String, Boolean> existingLoginResponse = new HashMap<>();
                    existingLoginResponse.put("sessionExists", true);
                    return new LoginValidationResult(
                            ResponseEntity.ok(new Response(Messages.ALREADY_LOGGEDIN, existingLoginResponse)));
                }
            }

        } else {

            if (isAlreadyLoggedIn(user)) {
                Map<String, Boolean> existingLoginResponse = new HashMap<>();
                existingLoginResponse.put("sessionExists", true);
                return new LoginValidationResult(
                        ResponseEntity.ok(new Response(Messages.ALREADY_LOGGEDIN, existingLoginResponse)));
            }

            if (props.isDbEnabled()) {
                loginSessionMgmt = loginSessionMgmtRepo.findByPhoneMobileAndRoleId(mobile, role).orElse(null);
                if (loginSessionMgmt != null) {
                    Map<String, Boolean> existingLoginResponse = new HashMap<>();
                    existingLoginResponse.put("sessionExists", true);
                    return new LoginValidationResult(
                            ResponseEntity.ok(new Response(Messages.ALREADY_LOGGEDIN, existingLoginResponse)));
                }
            }
        }

        return new LoginValidationResult(user);
    }

    // ================= COMMON METHOD =================

    private boolean isAlreadyLoggedIn(User user) {

        Optional<LoginResponseDto> redisCheck = authRedisRepository.getByUser(user.getUdiseSchCode(),
                user.getPhoneMobile());
        return redisCheck.isPresent();
    }

    private void saveLoginSession(User user, Short role, TokenResponse accessTokenResponse,
            TokenResponse refreshTokenResponse, String accessTokenExpiry, String refreshTokenExpiry,
            HttpServletRequest httpServletRequest) {

        if (!props.isDbEnabled()) {
            return;
        }

        Optional<UserRoleMap> userRoleMapObj = userRoleMapRepository.findByUser_UserIdAndRole_RoleId(user.getUserId(),
                role);

        // ===== SAVE IN MAIN TABLE =====
        LoginSessionMgmt loginSessionObj = new LoginSessionMgmt();

        loginSessionObj.setAccessToken(accessTokenResponse.getToken());
        loginSessionObj.setAccessTokenExpiry(accessTokenExpiry);
        loginSessionObj.setRefreshToken(refreshTokenResponse.getToken());
        loginSessionObj.setRefreshTokenExpiry(refreshTokenExpiry);
        loginSessionObj.setUserId(user.getUserId());
        loginSessionObj.setRoleId(role);
        loginSessionObj.setPhoneMobile(user.getPhoneMobile());
        loginSessionObj.setUdiseCode(user.getUdiseSchCode());
        loginSessionObj.setIpAddress(httpServletRequest.getRemoteAddr());
        loginSessionObj.setUserAgentBrowserDevice(httpServletRequest.getHeader("User-Agent"));
        loginSessionObj.setLocation(httpServletRequest.getLocalAddr());
        loginSessionObj.setCreatedBy(user.getUserId());
        loginSessionObj.setCreatedTime(LocalDateTime.now());

        userRoleMapObj.ifPresent(userRoleMap -> loginSessionObj.setEntityId(userRoleMap.getRoleEntityId()));

        LoginSessionMgmt savedSession = loginSessionMgmtRepo.save(loginSessionObj);

        // ===== SAVE IN HISTORY TABLE =====
        saveLoginSessionHistory(savedSession);
    }

    private void saveLoginSessionHistory(LoginSessionMgmt savedSession) {

        Short loginType = 1;

        LoginSessionMgmtHistory hist = new LoginSessionMgmtHistory();

        hist.setLoginId(savedSession.getLoginId());
        hist.setUserId(savedSession.getUserId());
        hist.setRoleId(savedSession.getRoleId());
        hist.setPhoneMobile(savedSession.getPhoneMobile());
        hist.setUdiseCode(savedSession.getUdiseCode());
        hist.setAccessToken(savedSession.getAccessToken());
        hist.setAccessTokenExpiry(savedSession.getAccessTokenExpiry());
        hist.setRefreshToken(savedSession.getRefreshToken());
        hist.setRefreshTokenExpiry(savedSession.getRefreshTokenExpiry());
        hist.setIpAddress(savedSession.getIpAddress());
        hist.setUserAgentBrowserDevice(savedSession.getUserAgentBrowserDevice());
        hist.setLocation(savedSession.getLocation());
        hist.setCreatedBy(savedSession.getCreatedBy());
        hist.setCreatedTime(LocalDateTime.now());
        hist.setLoginType(loginType);
        hist.setEntityId(savedSession.getEntityId());

        loginSessionMgmtHistoryRepo.save(hist);
    }

    @Override
    public ResponseEntity<?> removeExistingLogin(LogoutRequest request) {

        try {
            LoginSessionMgmt loginObj;
            User user;
            if (request.getRole() == 1) {

                if (StringUtils.isBlank(request.getUdiseCode())) {
//                    throw new ValidationException("UDISE Code is required");
                    return ResponseEntity.ok().body(new Response(Messages.UDISE_REQUIRED));
                }

                user = userRepository.findByUdiseAndRole(request.getUdiseCode(), request.getRole()).orElse(null);

                if (user == null) {
                    return ResponseEntity.ok().body(new Response(Messages.INVALID_CRED));
                }
                authRedisRepository.deleteByUser(user.getUdiseSchCode(), user.getPhoneMobile());

                loginObj = loginSessionMgmtRepo.findByUdiseCodeAndRoleId(request.getUdiseCode(), request.getRole());
//                if (loginObj == null) {
//                    return ResponseEntity.ok().body(new Response(Messages.INVALID_CRED));
//                }
            }

            else if (request.getRole() == 2) {

                if (StringUtils.isBlank(request.getMobile())) {
//                    throw new ValidationException("Mobile is required");
                    return ResponseEntity.ok().body(new Response(Messages.MOBILE_REQUIRED));
                }

                if (StringUtils.isBlank(request.getUdiseCode())) {
//                    throw new ValidationException("UDISE Code is required");
                    return ResponseEntity.ok().body(new Response(Messages.UDISE_REQUIRED));
                }

                user = userRepository.findByUdiseAndRoleAndMobile(request.getUdiseCode(), request.getRole(), request.getMobile()).orElse(null);

                if (user == null) {
                    return ResponseEntity.ok().body(new Response(Messages.INVALID_CRED));
                }
                authRedisRepository.deleteByUser(user.getUdiseSchCode(), user.getPhoneMobile());

                loginObj = loginSessionMgmtRepo.findByUdiseCodeAndRoleIdAndPhoneMobile(request.getUdiseCode(), request.getRole(), request.getMobile()).orElse(null);
//                if (loginObj == null) {
//                    return ResponseEntity.ok().body(new Response(Messages.INVALID_CRED));
//                }
            }

            else {

                if (StringUtils.isBlank(request.getMobile())) {
//                    throw new ValidationException("Mobile is required");
                    return ResponseEntity.ok().body(new Response(Messages.MOBILE_REQUIRED));
                }
                List<User> seUser = userRepository.findByPhoneMobileAndRole(request.getMobile(), request.getRole());
                user = seUser.get(0);
                if (user == null) {
                    return ResponseEntity.ok().body(new Response(Messages.INVALID_CRED));
                }
                authRedisRepository.deleteByUser(user.getUdiseSchCode(), user.getPhoneMobile());

                loginObj = loginSessionMgmtRepo.findByPhoneMobileAndRoleId(request.getMobile(),request.getRole()).orElse(null);

//                if (loginObj == null) {
//                    return ResponseEntity.ok().body(new Response(Messages.INVALID_CRED));
//                }

            }

            if (props.isDbEnabled()) {
                if(loginObj != null) {
                    loginSessionMgmtRepo.deleteById(loginObj.getLoginId());

                    LoginSessionMgmtHistory loginSessionHistObj = new LoginSessionMgmtHistory();

                    loginSessionHistObj.setLoginId(loginObj.getLoginId());
                    loginSessionHistObj.setUserId(loginObj.getUserId());
                    loginSessionHistObj.setEntityId(loginObj.getEntityId());
                    loginSessionHistObj.setRoleId(loginObj.getRoleId());
                    loginSessionHistObj.setPhoneMobile(loginObj.getPhoneMobile());
                    loginSessionHistObj.setUdiseCode(loginObj.getUdiseCode());
                    loginSessionHistObj.setAccessToken(loginObj.getAccessToken());
                    loginSessionHistObj.setAccessTokenExpiry(loginObj.getAccessTokenExpiry());
                    loginSessionHistObj.setRefreshToken(loginObj.getRefreshToken());
                    loginSessionHistObj.setRefreshTokenExpiry(loginObj.getRefreshTokenExpiry());
                    loginSessionHistObj.setIpAddress(loginObj.getIpAddress());
                    loginSessionHistObj.setUserAgentBrowserDevice(loginObj.getUserAgentBrowserDevice());
                    loginSessionHistObj.setLocation(loginObj.getLocation());
                    loginSessionHistObj.setCreatedBy(user.getUserId());
                    loginSessionHistObj.setCreatedTime(LocalDateTime.now());
                    loginSessionHistObj.setLoginType((short) 2);

                    loginSessionMgmtHistoryRepo.save(loginSessionHistObj);


                    Optional<LoginSessionMgmtHistory> existingLoginHst = loginSessionMgmtHistoryRepo
                            .findByLoginIdAndLoginType(loginObj.getLoginId(), (short) 1);

                    if (existingLoginHst.isPresent()) {
                        LoginSessionMgmtHistory loginSessionMgmtHistory = existingLoginHst.get();
                        loginSessionMgmtHistory.setModifiedBy(user.getUserId());
                        loginSessionMgmtHistory.setModifiedTime(LocalDateTime.now());
                        loginSessionMgmtHistoryRepo.save(loginSessionMgmtHistory);
                    }
                }

            }
            return ResponseEntity.ok().body(new Response(Messages.LOGOUT_SUCCESS));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @Override
    public ResponseEntity<?> loginWithMobile(MobileRequest request, HttpServletRequest httpServletRequest) {
        try {

            User user;
            if (request.getRole() == 1) {

                if (StringUtils.isBlank(request.getUdiseCode())) {
//                    throw new ValidationException("UDISE Code is required");
                    return ResponseEntity.ok().body(new Response(Messages.UDISE_REQUIRED));
                }

                user = userRepository.findByUdiseAndRole(request.getUdiseCode(), request.getRole()).orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.HM_NOT_REGISTERED));
                }

                boolean hasRole = user.getUserRole().stream().anyMatch(ur -> ur.getRole().getRoleId().equals(request.getRole())
                                && ur.getRole().getIsActive() == 1);

                if (!hasRole) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Messages.ROLE_NOT_ASSIGNED));
                }

            } else if (request.getRole() == 2) {

                if (StringUtils.isBlank(request.getMobile())) {
//                    throw new ValidationException("Mobile is required");
                    return ResponseEntity.ok().body(new Response(Messages.MOBILE_REQUIRED));
                }

                if (StringUtils.isBlank(request.getUdiseCode())) {
//                    throw new ValidationException("UDISE Code is required");
                    return ResponseEntity.ok().body(new Response(Messages.UDISE_REQUIRED));
                }

                user = userRepository
                        .findByUdiseAndRoleAndMobile(request.getUdiseCode(), request.getRole(), request.getMobile())
                        .orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NOT_REGISTERED));
                }
            } else {
                if (StringUtils.isBlank(request.getMobile())) {
//                    throw new ValidationException("Mobile is required");
                    return ResponseEntity.ok().body(new Response(Messages.MOBILE_REQUIRED));
                }
                List<User> seUser = userRepository.findByPhoneMobileAndRole(request.getMobile(), request.getRole());

                if(!seUser.isEmpty()){
                    user = seUser.get(0);
                    if (user == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.SE_NOT_REGISTERED));
                    }
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.SE_NOT_REGISTERED));

                }

            }

            // VALIDATE USER
            LoginValidationResult result = validateUserLogin(request.getUdiseCode(), request.getMobile(),request.getRole(), user);

            if (result.hasError()) {
                return result.getErrorResponse();
            }

            List<RoleMaster> roles = user.getUserRole().stream().filter(ur -> ur.getIsActive() == 1)
                    .map(UserRoleMap::getRole).toList();

            if (roles.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.INVALID_ROLE_ID));
            }

            Short roleId = request.getRole(); // auto-select first role

            String username;

            if (request.getRole() == 1) {
                username = "1:" + request.getUdiseCode() + ":-";
            } else if (request.getRole() == 2) {
                username = request.getRole() + ":" + request.getUdiseCode() + ":" + request.getMobile();
            } else {
                username = request.getRole() + ":" + "-:" + request.getMobile();
            }

            TokenResponse accessTokenResponse = jwtUtil.generateAccessToken(username);
            TokenResponse refreshTokenResponse = jwtUtil.generateRefreshToken(username);

            Instant accessTokenInstant = accessTokenResponse.getExpiryTime().toInstant();
            Instant refreshTokenInstant = refreshTokenResponse.getExpiryTime().toInstant();

            LocalDateTime accessTokenExpiryDateTime = LocalDateTime.ofInstant(accessTokenInstant,
                    ZoneId.systemDefault());
            LocalDateTime refreshTokenExpiryDateTime = LocalDateTime.ofInstant(refreshTokenInstant,
                    ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String accessTokenExpiry = accessTokenExpiryDateTime.format(formatter);
            String refreshTokenExpiry = refreshTokenExpiryDateTime.format(formatter);

            // Fetch role module permissions
            List<RoleModulePermission> permissions = roleModulePermissionRepository.findByRoleIdAndIsActive(roleId,
                    (short) 1);

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

            // Create an empty role - entity list
//            List<UserRoleEntity> roleEntityList = null;

            List<UserRoleMap> roleEntityMaps = userRoleMapRepository.findByUser_UserId(user.getUserId());

            List<UserRoleEntity> roleEntityList = new ArrayList<>();

            for (UserRoleMap r : roleEntityMaps) {
                roleEntityList.add(new UserRoleEntity(r.getRole().getRoleId(), r.getRoleEntityId()));
            }

            UserDto userDto = new UserDto(user, roles, moduleMap, roleEntityList);

            LoginResponseDto responseDto = new LoginResponseDto();

            responseDto.setAccessToken(accessTokenResponse.getToken());
            responseDto.setRefreshToken(refreshTokenResponse.getToken());
            responseDto.setAccessTokenExpiry(accessTokenExpiry);
            responseDto.setRefreshTokenExpiry(refreshTokenExpiry);
            responseDto.setTokenType("Bearer");
            responseDto.setUser(userDto);
            // save tokens to redis
            authRedisRepository.saveTokens(userDto.getUdiseCode(), userDto.getMobile(), responseDto);

            saveLoginSession(user, request.getRole(), accessTokenResponse, refreshTokenResponse, accessTokenExpiry,
                    refreshTokenExpiry, httpServletRequest);

            return ResponseEntity.ok(new Response(Messages.LOGIN_SUCCESS, responseDto));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @Override
    public ResponseEntity<?> refreshTokenLoginService(RefreshTokenRequest request,
            HttpServletRequest httpServletRequest) {
        try {

            String refreshToken = request.getRefreshToken();

            if (refreshToken == null) {
                return ResponseEntity.badRequest().body(new Response(new ErrorResponse(Messages.REQUIRED_REFRESH)));
            }

            // Validate token
            if (!jwtUtil.validateToken(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new Response(new ErrorResponse(Messages.INVALID_REFRESH_TOKEN)));
            }
            // If refresh token is logged out/deleted from Redis, refresh endpoint must
            // reject it even if JWT itself is still cryptographically valid.
            if (!authRedisRepository.isRefreshTokenActive(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new Response(new ErrorResponse(Messages.EXPIRE_REFRESH_TOKEN)));
            }

            String tokenType = jwtUtil.getClaim(refreshToken, "type");

            if (!"REFRESH".equals(tokenType)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new Response(new ErrorResponse(Messages.INVALID_TOKEN_TYPE)));
            }

            String username = jwtUtil.getUsernameFromToken(refreshToken);
            String[] parts = username.split(":");
            String role = parts[0];
            String udiseCode = parts[1];
            String mobile = parts[2];

            User user;

            if (role.equals("1")) {
                user = userRepository.findByUdiseAndRole(udiseCode, Short.valueOf(role)).orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.HM_NOT_REGISTERED));
                }
                boolean hasRole = user.getUserRole().stream().anyMatch(
                        ur -> ur.getRole().getRoleId().toString().equals(role) && ur.getRole().getIsActive() == 1);

                if (!hasRole) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Messages.ROLE_NOT_ASSIGNED));
                }
            } else if (role.equals("2")) {
                user = userRepository.findByUdiseAndRoleAndMobile(udiseCode, Short.valueOf(role), mobile).orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.NOT_REGISTERED));

                }
            } else {
                List<User> seUser = userRepository.findByPhoneMobileAndRole(mobile, Short.valueOf(role));

                user = seUser.get(0);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.SE_NOT_REGISTERED));
                }
            }

            List<RoleMaster> roles = user.getUserRole().stream().filter(r -> r.getIsActive() == 1)
                    .map(UserRoleMap::getRole).toList();

            List<RoleModulePermission> permissions = roleModulePermissionRepository
                    .findByRoleIdAndIsActive(Short.valueOf(role), (short) 1);

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

            // Create an empty role - entity list
            List<UserRoleMap> roleEntityMaps = userRoleMapRepository.findByUser_UserId(user.getUserId());

            List<UserRoleEntity> roleEntityList = new ArrayList<>();

            for (UserRoleMap r : roleEntityMaps) {
                roleEntityList.add(new UserRoleEntity(r.getRole().getRoleId(), r.getRoleEntityId()));
            }

            UserDto userDto = new UserDto(user, roles, moduleMap, roleEntityList);

            TokenResponse newAccessToken = jwtUtil.generateAccessToken(username);
            TokenResponse newRefreshToken = jwtUtil.generateRefreshToken(username);

            // access token
            Instant accessInstant = newAccessToken.getExpiryTime().toInstant();
            LocalDateTime accessTokenExpiryDateTime = LocalDateTime.ofInstant(accessInstant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String accessTokenExpiryFormatted = accessTokenExpiryDateTime.format(formatter);

            // refresh token
            Instant refreshInstant = newRefreshToken.getExpiryTime().toInstant();
            LocalDateTime refreshTokenExpiryDateTime = LocalDateTime.ofInstant(refreshInstant, ZoneId.systemDefault());
            String refreshTokenExpiryFormatted = refreshTokenExpiryDateTime.format(formatter);

            LoginResponseDto responseDto = new LoginResponseDto();
            responseDto.setAccessToken(newAccessToken.getToken());
            responseDto.setRefreshToken(newRefreshToken.getToken());
            responseDto.setAccessTokenExpiry(accessTokenExpiryFormatted);
            responseDto.setRefreshTokenExpiry(refreshTokenExpiryFormatted);
            responseDto.setTokenType("Bearer");
            responseDto.setUser(userDto);

            // Replace old stored tokens in redis by deleting old user mapping and saving
            // new data:
            // This keeps Redis aligned with latest active tokens.
            authRedisRepository.deleteByUser(userDto.getUdiseCode(), userDto.getMobile());
            authRedisRepository.saveTokens(userDto.getUdiseCode(), userDto.getMobile(), responseDto);

            updateLoginSession(user, Short.valueOf(role), newAccessToken, newRefreshToken, accessTokenExpiryFormatted,
                    refreshTokenExpiryFormatted);

            return ResponseEntity.ok(new Response(Messages.SUCCESS, responseDto));

        } catch (Exception e) {

            log.error("Refresh token error", e);

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(new ErrorResponse(Messages.EXPIRE_REFRESH_TOKEN)));
        }
    }

    private void updateLoginSession(User user, Short role, TokenResponse newAccessToken,
                                    TokenResponse newRefreshToken, String accessTokenExpiryFormatted,
                                    String refreshTokenExpiryFormatted) {
        try {

            Optional<LoginSessionMgmt> loginSessionMgmtObj = loginSessionMgmtRepo.findByUserIdAndRoleId(user.getUserId(), role);

            if (loginSessionMgmtObj.isPresent()) {
                LoginSessionMgmt loginSessionMgmt = loginSessionMgmtObj.get();

                loginSessionMgmt.setAccessToken(String.valueOf(newAccessToken.getToken()));
                loginSessionMgmt.setAccessTokenExpiry(accessTokenExpiryFormatted);
                loginSessionMgmt.setRefreshToken(String.valueOf(newRefreshToken.getToken()));
                loginSessionMgmt.setRefreshTokenExpiry(refreshTokenExpiryFormatted);
                loginSessionMgmt.setModifiedBy(user.getUserId());
                loginSessionMgmt.setModifiedTime(LocalDateTime.now());

                LoginSessionMgmt updatedSession = loginSessionMgmtRepo.save(loginSessionMgmt);
                log.info("Login Session Management updated successfully");

                updateLoginSessionHistory(updatedSession);
            }
        } catch (Exception e) {
            log.error("Unable to update Login Session Management: ", e);

            e.printStackTrace();
        }
    }

    private void updateLoginSessionHistory(LoginSessionMgmt updatedSession) {
        try {

            Optional<LoginSessionMgmtHistory> loginSessionMgmtHistoryObj =
                    loginSessionMgmtHistoryRepo.findByLoginIdAndLoginType(updatedSession.getLoginId(), (short) 1);

            if (loginSessionMgmtHistoryObj.isPresent()) {
                LoginSessionMgmtHistory loginSessionMgmtHistory = loginSessionMgmtHistoryObj.get();

                loginSessionMgmtHistory.setAccessToken(updatedSession.getAccessToken());
                loginSessionMgmtHistory.setAccessTokenExpiry(updatedSession.getAccessTokenExpiry());
                loginSessionMgmtHistory.setRefreshToken(updatedSession.getRefreshToken());
                loginSessionMgmtHistory.setRefreshTokenExpiry(updatedSession.getRefreshTokenExpiry());
                loginSessionMgmtHistory.setModifiedBy(updatedSession.getModifiedBy());
                loginSessionMgmtHistory.setModifiedTime(LocalDateTime.now());

                loginSessionMgmtHistoryRepo.save(loginSessionMgmtHistory);
                log.info("Login Session Management History updated successfully");
            }

        } catch (Exception e) {
            log.error("Unable to update Login Session Management History: ", e);

            e.printStackTrace();
        }
    }

}
