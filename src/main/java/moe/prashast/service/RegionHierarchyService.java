package moe.prashast.service;

import moe.prashast.bean.RegionHierarchyRequestBean;
import moe.prashast.request.pojo.RegionHierarchyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RegionHierarchyService {

    String getRegionDetails(RegionHierarchyRequestBean request);

    ResponseEntity<?> fetchRegionDetails(RegionHierarchyRequest request);

    ResponseEntity<?> fetchUserList(RegionHierarchyRequest req);
}
