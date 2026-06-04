package moe.prashast.serviceImpl;

import jakarta.transaction.Transactional;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.entity.*;
import moe.prashast.repository.HierarchyMasterRepository;
import moe.prashast.repository.RoleMasterRepository;
import moe.prashast.repository.UserRepository;
import moe.prashast.repository.UserRoleMapRepository;
import moe.prashast.request.pojo.SignUpOthersReq;
import moe.prashast.service.SignUpService;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SignUpSerivceImpl implements SignUpService {

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleMapRepository userRoleMapRepository;

    @Autowired
    private HierarchyMasterRepository hierarchyMasterRepository;

    @Transactional
    @Override
    public ResponseEntity<?> saveDataOtherUsers(SignUpOthersReq req) {
        try{

            User user = new User();
            user.setUdiseSchCode(String.valueOf(req.getCode()));

           Optional<UserRoleMap> mobileCheck= userRoleMapRepository.findByUser_PhoneMobileAndRole_RoleId(req.getMobile(),req.getRole());
            if(mobileCheck.isEmpty()){
                user.setPhoneMobile(req.getMobile());
            }
            else {
                return ResponseEntity.ok().body(new Response(Messages.MOBILE_ALREADY_REG));
            }

            user.setName(req.getName());
            user.setUserPassword("");
            user.setCreatedTime(LocalDateTime.now());
            user.setCreatedBy(String.valueOf(req.getCode()));
            user.setSchoolId(0);
            user.setIsActive((short) 1);
            user.setIsApproved((short) 1);
            user.setStateId((short)0);

            Optional<UserRoleMap> emailCheck= userRoleMapRepository.findByUser_EmailIdAndRole_RoleId(req.getEmail(),req.getRole());
            if(emailCheck.isEmpty()){
                user.setEmailId(req.getEmail());

            }
            else {
                return ResponseEntity.ok().body(new Response(Messages.EMAIL_ALREADY_REG));
            }

            user.setHierarchyId(req.getHierarchyId());

            user.setParentId(req.getParentId());


            RoleMaster roleMaster = roleMasterRepository.findById(req.getRole()).orElse(null);

            if(roleMaster==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Messages.INVALID_ROLE_ID));

            }
            UserRoleMap userRoleMap = new UserRoleMap();

            userRoleMap.setRole(roleMaster);
            userRoleMap.setIsActive((short) 1);
            userRoleMap.setCreatedTime(LocalDateTime.now());
            userRoleMap.setCreatedBy(String.valueOf(req.getCode()));

            HierarchyMaster regionData = hierarchyMasterRepository.findByHierarchyIdAndParentId(req.getHierarchyId(),req.getParentId());

            Short role=  req.getRole();
            if(role==12){
                userRoleMap.setRoleEntityId(Long.valueOf(regionData.getStateId()));
            }
            else if (role==13) {
                userRoleMap.setRoleEntityId(Long.valueOf(regionData.getDistrictId()));
            }
            else if (role==14) {
                userRoleMap.setRoleEntityId(Long.valueOf(regionData.getBlockId()));
            }

            User savedUser = userRepository.save(user);
            userRoleMap.setUser(savedUser);
            userRoleMapRepository.save(userRoleMap);

            return ResponseEntity.ok(new Response(Messages.DATA_SAVE_OTHER));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }
}
