package moe.prashast.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import moe.prashast.constant.Messages;
import moe.prashast.dto.*;
import moe.prashast.entity.*;
import moe.prashast.repository.*;
import moe.prashast.request.pojo.*;
import moe.prashast.security.service.CustomUserDetails;
import moe.prashast.service.CaptchaService;
import moe.prashast.service.SchoolService;
import moe.prashast.service.SignUpService;
import moe.prashast.util.UdiseValidationUtil;
import moe.prashast.validation.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/auth/api")
public class SignUpController {

    @Autowired
    private UdiseValidationUtil udiseValidationService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${udise.base.api.url}")
    private String udiseBaseApiUrl;

    @Value("${clientId}")
    String clientId;

    @Value("${clientSecret}")
    String clientSecret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SchoolMasterLiveCoreRepository schMastLiveCoreRepo;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private UserRoleMapRepository userRoleMapRepository;

    @Autowired
    private TeacherSectionAssignmentRepository assignmentRepository;

    @Autowired
    private TeacherProfilePrstRepository teacherProfilePrstRepository;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private CommonValidation commonValidation;


    public String authenticate() throws Exception {
        Map<String, String> requestPayLoad = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String appKey = udiseValidationService.getOrCreateAppKey();
        String plainJson = "{"
                + "\"appKey\":\"" + appKey + "\","
                + "\"clientId\":\""+clientId+"\","
                + "\"clientSecret\":\""+clientSecret+"\""
                + "}";

        String payLoadEncription = udiseValidationService.getEncriptedData(plainJson,
                udiseValidationService.getPublicKeyBase64());
        requestPayLoad.put("data", payLoadEncription);
        return objectMapper.writeValueAsString(requestPayLoad);
    }

    public Map<String, String> generateToken() {

        Map<String, String> result = new HashMap<>();

        try {

            String authDataPayload = authenticate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(authDataPayload, headers);

            ResponseEntity<AuthDataResponse> httpResponse = restTemplate.exchange(
                    udiseBaseApiUrl + "/public-api/v1.3/authenticate",
                    HttpMethod.POST,
                    requestEntity,
                    AuthDataResponse.class);

            AuthDataResponse response = httpResponse.getBody();

            if (response == null || !Boolean.TRUE.equals(response.getStatus())) {
                throw new RuntimeException("UDISE authentication failed: "
                        + (response != null ? response.getErrorDetails() : "No response body"));
            }

            if (response.getData() == null || response.getData().isTextual()) {
                throw new RuntimeException("Invalid auth data received from UDISE");
            }

            ObjectMapper mapper = new ObjectMapper();
            AuthData authData = mapper.treeToValue(response.getData(), AuthData.class);

            result.put("authToken", authData.getAuthToken());
            result.put("sek", authData.getSek());
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to generate UDISE token", ex);
        }
    }

    @Transactional
    @PostMapping("/save-data-sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpPojo requestBody) {

        try {
            String udiseCode = requestBody.getUdiseCode();
            String mobileNo = requestBody.getMobileNo();
            Map<String, String> tokenMap = generateToken();

//            String validationMessage = commonValidation.udiseCodeAndMobileValidation(requestBody.getUdiseCode(),requestBody.getMobileNo());
//            if (validationMessage != null ) {
//                return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION,validationMessage)));
//            }
//
//            String nameAndRole= commonValidation.checkNameAndRole(requestBody.getFullName(),requestBody.getRole());
//            if (nameAndRole != null) {
//                return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION,nameAndRole)));
//            }


            if (tokenMap == null || !tokenMap.containsKey("authToken")) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to generate auth token");
            }

            String authToken = tokenMap.get("authToken");
            String sek = tokenMap.get("sek");
            String decodedSek = new String(Base64.getDecoder().decode(sek));

            String appKey = udiseValidationService.getOrCreateAppKey();

            String decryptedSek = udiseValidationService.decrypt(decodedSek, appKey);

            ObjectMapper mapper = new ObjectMapper();

            Map<String, String> plainPayload = new HashMap<>();
            plainPayload.put("udiseCode", udiseCode);

            String plainJson = mapper.writeValueAsString(plainPayload);

            String encryptedPayload = udiseValidationService.encryptWithSek(plainJson, decryptedSek);

            Map<String, String> finalBody = new HashMap<>();
            finalBody.put("data", encryptedPayload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            headers.setBearerAuth(authToken);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(finalBody, headers);
            // to save in DB
                List<User> byMobileAndUdiseCode = userRepository.findByPhoneMobileAndUdiseSchCode(mobileNo.trim(), udiseCode.trim());

                if (!byMobileAndUdiseCode.isEmpty()) {
                    return ResponseEntity.ok(new Response(Messages.DATA_ALREADY_PRESENT));
                }
                ResponseEntity<UdiseCodeResponse> response = restTemplate.exchange(
                        udiseBaseApiUrl + "/public-api/v1.1/school-info/by-udise-code/public",
                        HttpMethod.POST,
                        requestEntity,
                        UdiseCodeResponse.class);

                if (response.getBody() == null) {
                    return ResponseEntity.ok(new Response(Messages.ERROR) );
                }

                // update in school master live core table
                UdiseCodeResponse body = response.getBody();
                if (Boolean.TRUE.equals(body.getStatus()) && body.getData() != null) {
                    UdiseSchoolData data =mapper.treeToValue(body.getData(), UdiseSchoolData.class);
                    schoolService.updateAndCopy(data);
                }

                Optional<SchoolMasterLiveCore> schoolMaster = schMastLiveCoreRepo.findByUdiseSchCode(udiseCode);

                User user = new User();
                user.setUdiseSchCode(udiseCode);
                user.setPhoneMobile(requestBody.getMobileNo());
                user.setName(requestBody.getFullName());
                user.setUserPassword("");
                user.setCreatedTime(LocalDateTime.now());
                user.setCreatedBy(udiseCode);
//                user.setModifiedTime(LocalDateTime.now());
                if(schoolMaster.isPresent()){
                    user.setSchoolId(schoolMaster.get().getSchoolId());
                }
                else {
                    user.setSchoolId(0);
                }
//                user.setModifiedBy(udiseCode);
                user.setIsActive((short) 1);
                user.setIsApproved((short) 1);
                if(schoolMaster.isPresent()){
                    user.setStateId(schoolMaster.get().getStateId());

                }
                else {
                    user.setStateId((short)0);
                }


            RoleMaster roleMaster = roleMasterRepository.findById(requestBody.getRole()).orElse(null);

            if(roleMaster==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.INVALID_ROLE_ID));

            }
            UserRoleMap userRoleMap = new UserRoleMap();

            userRoleMap.setRole(roleMaster);
            userRoleMap.setIsActive((short) 1);
            userRoleMap.setCreatedTime(LocalDateTime.now());
            userRoleMap.setModifiedTime(LocalDateTime.now());
            userRoleMap.setModifiedBy(udiseCode);
            userRoleMap.setCreatedBy(udiseCode);
            if (requestBody.getRole() == 1) {

                if (schoolMaster.isEmpty()) {
                    return ResponseEntity.ok(new Response(Messages.SCHOOL_NOT_FOUND));
                }
                userRoleMap.setRoleEntityId( Long.valueOf(schoolMaster.get().getSchoolId()) );

            } else {

                Optional<TeacherProfilePrst> teacherOpt = teacherProfilePrstRepository.findByMobile(requestBody.getMobileNo());

                if (teacherOpt.isEmpty()) {
                    return ResponseEntity.ok(new Response(Messages.TEACHER_NOT_FOUND));
                }

                TeacherProfilePrst teacher = teacherOpt.get();
                List<TeacherSectionAssignment> assignments =assignmentRepository.findByAssignTeacherId( String.valueOf(teacher.getEmpStaffId()) );

                if (assignments == null || assignments.isEmpty()) {
                    return ResponseEntity.ok(new Response(Messages.NOT_ASSIGNED));
                }

                Optional<TeacherSectionAssignment> activeAssignment =assignments.stream()
                                .filter(a -> a.getAssignStatus() == 1)
                                .findFirst();

                if (activeAssignment.isEmpty()) {
                    return ResponseEntity.ok(new Response(Messages.NOT_ASSIGNED));
                }
                userRoleMap.setRoleEntityId(Long.valueOf(activeAssignment.get().getAssignTeacherId()));
            }
            User savedUser = userRepository.save(user);
            userRoleMap.setUser(savedUser);
            userRoleMapRepository.save(userRoleMap);

            return ResponseEntity.ok(new Response(Messages.DATA_SAVE));

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping("/validate-udise-code")
    public ResponseEntity<?> validateUdiseCode( @Valid @RequestBody ValidateRequest requestBody ) throws NoSuchAlgorithmException {
        try{


//            if (requestBody.getIsCheck() != null && requestBody.getIsCheck() == 1) {
//                boolean isValidCaptcha = false;
//                if (requestBody.getCaptchaId() != null || requestBody.getCaptchaValue() != null) {
//                    isValidCaptcha = captchaService.validateCaptcha(requestBody.getCaptchaId(), requestBody.getCaptchaValue());
//                } else {
//                    return ResponseEntity.ok(new Response(Messages.CAPTCHA_REQUIRED));
//                }
//                if (!isValidCaptcha) {
//                    return ResponseEntity.ok(new Response(new ErrorResponse(Messages.INVALID_CAPTCHA)));
//                }
//            }


//            String validationMessage = commonValidation.udiseCodeAndMobileValidation(requestBody.getUdiseCode(),requestBody.getMobileNo());
//            if (validationMessage != null) {
//                return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION,validationMessage)));
//            }
//
//
//            List<User> byMobileAndUdiseCode = userRepository.findByPhoneMobileAndUdiseSchCode(requestBody.getMobileNo().trim(), requestBody.getUdiseCode().trim());
//
//            if (!byMobileAndUdiseCode.isEmpty()) {
//                return ResponseEntity.ok(new Response(Messages.DATA_ALREADY_PRESENT));
//            }


            List<User> byMobileAndUdiseCode = userRepository.findByPhoneMobileAndUdiseSchCode(requestBody.getMobileNo().trim(), requestBody.getUdiseCode().trim());

            if (!byMobileAndUdiseCode.isEmpty()) {
                return ResponseEntity.ok(new Response(Messages.DATA_ALREADY_PRESENT));
            }


            String udiseCode = requestBody.getUdiseCode();
            Map<String, String> tokenMap = generateToken();

            if (tokenMap == null || !tokenMap.containsKey("authToken")) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to generate auth token");
            }

            String authToken = tokenMap.get("authToken");
            String sek = tokenMap.get("sek");
            String decodedSek = new String(Base64.getDecoder().decode(sek));

            String appKey = udiseValidationService.getOrCreateAppKey();

            String decryptedSek = udiseValidationService.decrypt(decodedSek, appKey);

            ObjectMapper mapper = new ObjectMapper();

            Map<String, String> plainPayload = new HashMap<>();

            plainPayload.put("udiseCode", udiseCode);
            plainPayload.put("mobile", requestBody.getMobileNo());

            String plainJson = mapper.writeValueAsString(plainPayload);

            String encryptedPayload = udiseValidationService.encryptWithSek(plainJson, decryptedSek);

            Map<String, String> finalBody = new HashMap<>();
            finalBody.put("data", encryptedPayload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            headers.setBearerAuth(authToken);
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(finalBody, headers);

            ResponseEntity<UdiseCodeResponse> response = restTemplate.exchange(
                        udiseBaseApiUrl + "/public-api/v1.1/check-mobile-number/public",
                        HttpMethod.POST, requestEntity,UdiseCodeResponse.class);

            UdiseCodeResponse responseBody = response.getBody();

//            JsonNode errorDetails = responseBody.getErrorDetails();
            String validMobileNo = responseBody.getData() != null ? responseBody.getData().asText() : null;

            if (validMobileNo != null && validMobileNo.equals("Y")) {

                return ResponseEntity.ok(new Response(Messages.UDISE_SUCCESS));
            }

            // for check errorDetails null
//            else if (errorDetails != null && !errorDetails.isNull()) {
//                return ResponseEntity.ok( new Response(false,
//                                HttpStatus.BAD_REQUEST.value(),errorDetails, errorDetails) );
//            }


            else if (validMobileNo != null && validMobileNo.equals("N")) {

//                return ResponseEntity
//                        .status(HttpStatus.UNPROCESSABLE_ENTITY)
//                        .body(new Response(false,HttpStatus.UNPROCESSABLE_ENTITY.value(),
//                                Messages.INVALID_UDISE_CODE_MOBILE_NO,null  ));

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response(false,HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                Messages.INVALID_UDISE_CODE_MOBILE_NO,null  ));
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((new Response(Messages.INVALID_UDISE_CODE_MOBILE_NO)));
        }
        catch (Exception e) {
        	e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Messages.ERROR));
        }
    }

    @GetMapping("/fetch-school-info/{udiseCode}")
    public ResponseEntity<?> fetchSchoolInformation(@PathVariable String udiseCode){
        try{
            Map<String, String> tokenMap = generateToken();
            if (tokenMap == null || !tokenMap.containsKey("authToken")) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to generate auth token");
            }


//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            CustomUserDetails user =(CustomUserDetails) authentication.getPrincipal();
//            System.out.println(user.getFullName());
//            System.out.println(user.getUdiseCode());


            String authToken = tokenMap.get("authToken");
            String sek = tokenMap.get("sek");
            String decodedSek = new String(Base64.getDecoder().decode(sek));

            String appKey = udiseValidationService.getOrCreateAppKey();

            String decryptedSek = udiseValidationService.decrypt(decodedSek, appKey);

            ObjectMapper mapper = new ObjectMapper();

            Map<String, String> plainPayload = new HashMap<>();
            plainPayload.put("udiseCode", udiseCode);

            String plainJson = mapper.writeValueAsString(plainPayload);

            String encryptedPayload = udiseValidationService.encryptWithSek(plainJson, decryptedSek);

            Map<String, String> finalBody = new HashMap<>();
            finalBody.put("data", encryptedPayload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            headers.setBearerAuth(authToken);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(finalBody, headers);

            ResponseEntity<UdiseCodeResponse> response = restTemplate.exchange(
                    udiseBaseApiUrl + "/public-api/v1.1/school-info/by-udise-code/public",
//                    udiseBaseApiUrl + "/public-api/v1.1/school-info-basic/with-contact-details/public",
                    HttpMethod.POST,
                    requestEntity,
                    UdiseCodeResponse.class);
            
            System.out.println("response---"+response);

            if (response.getBody() == null || !response.getBody().getStatus()) {
                return ResponseEntity.ok(new Response(Messages.INVALID_UDISE_CODE) );
            }

        // update in school master live core table

//            UdiseCodeResponse body = response.getBody();
//            if (Boolean.TRUE.equals(body.getStatus()) && body.getData() != null) {
//
//                UdiseSchoolData data =mapper.treeToValue(body.getData(), UdiseSchoolData.class);
//                schoolService.updateAndCopy(data);
//
//            }


            return ResponseEntity.ok(new Response(Messages.SUCCESS ,response.getBody().getData()));
        }
        catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(Messages.ERROR));
        }

    }

    @PostMapping("/set-password")
    public ResponseEntity<?> setPassword(@Valid @RequestBody SetPasswordRequest request){
        try{

//            String validationMessage = commonValidation.udiseCodeAndMobileValidation(request.getUdiseCode(),request.getMobile());
//            if (validationMessage != null) {
//                return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION,validationMessage)));
//            }
//
//            String passwordValidation = commonValidation.passwordValidation(request.getPassword(),request.getRetypePassword());
//            if (passwordValidation != null) {
//                return ResponseEntity.ok(new Response(new ErrorResponse(Messages.VALIDATION,passwordValidation)));
//            }


            if (!request.getPassword().equals(request.getRetypePassword())) {
                return ResponseEntity.ok(new Response(Messages.PASSWORD_ERROR));

            }
            List<User> userList = userRepository.findByPhoneMobileAndUdiseSchCode(request.getMobile(), request.getUdiseCode());
            if(userList.isEmpty()){
                return ResponseEntity.ok(new Response(Messages.USER_NOT_FOUND));

            }
                User user = userList.get(0);

                if (user.getUserPassword() != null && !user.getUserPassword().trim().isEmpty()) {
                    return ResponseEntity.ok(new Response(Messages.PASSWORD_SET));
                }

                user.setUserPassword(passwordEncoder.encode(request.getPassword()));
                user.setModifiedBy(request.getUdiseCode());
                user.setModifiedTime(LocalDateTime.now());
//            user.setStatus(1); // 1 = active
                userRepository.save(user);

                return ResponseEntity.ok(new Response(Messages.PASSWORD_SAVE_SUCCESS));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity .status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error setting password: " + e.getMessage());
        }

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordReq req) {

        try {

            if (!Objects.equals(req.getPassword(), req.getRetypePassword())) {
                return ResponseEntity.ok(new Response(Messages.PASSWORD_ERROR));
            }

            String encodedPassword = passwordEncoder.encode(req.getPassword());

            // ROLE 1
            if (req.getRoleId() == 1) {

                User user = userRepository.findByUdiseAndRole(req.getUdiseCode(), req.getRoleId()) .orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.MOBILE_NOT_FOUND));
                }

                updateUserPassword(user, encodedPassword, req.getUdiseCode());

            }

            // ROLE 2
            else if (req.getRoleId() == 2) {

                User user = userRepository.findByUdiseAndRoleAndMobile(req.getUdiseCode(), req.getRoleId(), req.getMobile()).orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.MOBILE_NOT_FOUND));
                }

                updateUserPassword(user, encodedPassword, req.getUdiseCode());
            }

            // ROLE 3 (MULTIPLE USERS)
            else {

                List<User> userList = userRepository.findMultipleUserByPhoneMobileAndRole(req.getMobile(), req.getRoleId());

                if (userList == null || userList.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.MOBILE_NOT_FOUND));
                }

                for (User user : userList) {
                    updateUserPassword(user, encodedPassword, req.getUdiseCode());
                }
            }

            return ResponseEntity.ok(new Response(Messages.PASS_RESET_SUCCESS));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    private void updateUserPassword(User user, String password, String modifiedBy) {
        user.setUserPassword(password);
        user.setModifiedBy(modifiedBy);
        user.setModifiedTime(LocalDateTime.now());
        userRepository.save(user);
    }

    @PostMapping("/check-user")
    public ResponseEntity<?> checkUser(@Valid @RequestBody UdiseCodeMobileReq req){
        try{

            List<User> userData = userRepository.findByUdiseSchCode(req.getUdiseCode());
            if(!userData.isEmpty()){
                return ResponseEntity.ok(new Response(Messages.CHECK_USER));
            }
            else {
                return ResponseEntity.ok(new Response(Messages.NO_DATA_FOUND));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }

    }

    @PostMapping("/save-data-sign-up-other")
    public ResponseEntity<?> saveDataOtherUsers(@Valid @RequestBody SignUpOthersReq req){
        try{
            return signUpService.saveDataOtherUsers(req);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }


}
