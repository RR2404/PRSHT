package moe.prashast.serviceImpl;

import io.swagger.v3.oas.models.OpenAPI;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.Response;
import moe.prashast.entity.User;
import moe.prashast.repository.UserRepository;
import moe.prashast.request.pojo.EditUserRequest;
import moe.prashast.request.pojo.UserIdRequest;
import moe.prashast.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagementServiceImpl implements   UserManagementService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> deleteUser(UserIdRequest req) {
        try{
            boolean exist = userRepository.existsById(req.getUserId());
            if(!exist){
                return ResponseEntity.ok().body(new Response(Messages.USER_NOT_FOUND));
            }
            userRepository.deleteById(req.getUserId());
            return ResponseEntity.ok().body(new Response(Messages.USER_DELETED));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(new Response(new ErrorResponse(Messages.ERROR)));
        }
    }

    @Override
    public ResponseEntity<?> editUser(EditUserRequest req) {
        try {
           Optional<User> existingUser= userRepository.findById(req.getUserId());
           if(existingUser.isEmpty()){
               return ResponseEntity.ok().body(new Response(Messages.USER_NOT_FOUND));
           }
           else {
               User toUpdate=existingUser.get();
               toUpdate.setName(req.getName());
               toUpdate.setEmailId(req.getEmail());
               toUpdate.setPhoneMobile(req.getMobile());
               userRepository.save(toUpdate);
               return ResponseEntity.ok().body(new Response(Messages.UPDATE_USER));

           }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(new Response(new ErrorResponse(Messages.ERROR)));        }
    }
}
