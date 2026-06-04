package moe.prashast.service;

import moe.prashast.request.pojo.EditUserRequest;
import moe.prashast.request.pojo.UserIdRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserManagementService {
    ResponseEntity<?> deleteUser(UserIdRequest req);

    ResponseEntity<?> editUser(EditUserRequest req);
}
