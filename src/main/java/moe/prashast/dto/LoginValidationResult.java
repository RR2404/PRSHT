package moe.prashast.dto;

import lombok.*;
import moe.prashast.entity.User;
import org.springframework.http.ResponseEntity;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginValidationResult {

    private User user;
    private ResponseEntity<?> errorResponse;

    public LoginValidationResult(ResponseEntity<?> errorResponse) {
        this.errorResponse = errorResponse;
    }

    public LoginValidationResult(User user) {
        this.user = user;
    }

    public boolean hasError() {
        return errorResponse != null;
    }
}
