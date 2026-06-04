package moe.prashast.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import moe.prashast.bean.RegionHierarchyRequestBean;
import moe.prashast.constant.Messages;
import moe.prashast.dto.*;
import moe.prashast.entity.HierarchyMaster;
import moe.prashast.entity.RoleMaster;
import moe.prashast.entity.User;
import moe.prashast.entity.UserRoleMap;
import moe.prashast.repository.HierarchyMasterRepository;
import moe.prashast.repository.RegionHierarchyRepository;
import moe.prashast.repository.UserRepository;
import moe.prashast.request.pojo.RegionHierarchyRequest;
import moe.prashast.request.pojo.SchoolConfigRequest;
import moe.prashast.service.RegionHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegionHierarchyServiceImpl implements RegionHierarchyService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegionHierarchyRepository regionHierarchyRepository;

    @Autowired
    private HierarchyMasterRepository hierarchyMasterRepository;

    @Autowired
    private UserRepository userMasterRepository;

    public String getRegionDetails(RegionHierarchyRequestBean request) {
        String jsonString = regionHierarchyRepository.getRegionDetails(request);

        try {
//            return objectMapper.readValue(jsonString, Object.class);
            return jsonString;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }

    }

    @Override
    public ResponseEntity<?> fetchRegionDetails(RegionHierarchyRequest request) {

        try {

            List<HierarchyMaster> hierarchyMasterList =hierarchyMasterRepository.findByHierarchyLevelAndParentId( request.getHierarchyLevel(), request.getParentId());

            if (hierarchyMasterList.isEmpty()) {

                return ResponseEntity.ok( new Response(Messages.NO_DATA_FOUND));
            }

            List<HierarchyMasterDto> dtoList =hierarchyMasterList.stream().map(HierarchyMasterDto::new).toList();

            return ResponseEntity.ok(new Response(Messages.SUCCESS, dtoList));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }

    @Override
    public ResponseEntity<?> fetchUserList(RegionHierarchyRequest req) {

        try {

            List<User> userlist =userMasterRepository.findByParentId(req.getParentId());

            List<OtherUserDto> userDtoList = userlist.stream().map(OtherUserDto::new).toList();

            if(userDtoList.isEmpty()){
                return ResponseEntity.ok( new Response(Messages.NO_DATA_FOUND));

            }

            return ResponseEntity.ok( new Response(Messages.SUCCESS, userDtoList));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));
        }
    }
}
