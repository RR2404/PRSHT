package moe.prashast.service;

import jakarta.servlet.http.HttpServletRequest;
import moe.prashast.dto.LoginRequest;
import moe.prashast.request.pojo.LogoutRequest;
import moe.prashast.request.pojo.MobileRequest;
import moe.prashast.request.pojo.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<?> login(LoginRequest request, HttpServletRequest httpServletRequest);

    ResponseEntity<?> removeExistingLogin(LogoutRequest request);

    ResponseEntity<?> loginWithMobile(MobileRequest request, HttpServletRequest httpServletRequest);

    ResponseEntity<?> refreshTokenLoginService(RefreshTokenRequest request, HttpServletRequest httpServletRequest);
}
