package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.request.pojo.EditUserRequest;
import moe.prashast.request.pojo.UserIdRequest;
import moe.prashast.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserIdRequest req){

        try {
            return userManagementService.deleteUser(req);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(new Response(new ErrorResponse(Messages.ERROR)));
        }

    }

    @PostMapping("/edit-user")
    public ResponseEntity<?> editUser(@Valid @RequestBody EditUserRequest req){
        try{

            return userManagementService.editUser(req);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(new Response(new ErrorResponse(Messages.ERROR)));        }
    }
}
