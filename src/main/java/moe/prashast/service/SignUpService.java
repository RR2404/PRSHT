package moe.prashast.service;

import moe.prashast.request.pojo.SignUpOthersReq;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SignUpService {
    ResponseEntity<?> saveDataOtherUsers(SignUpOthersReq req);
}
