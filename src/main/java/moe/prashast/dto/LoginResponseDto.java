package moe.prashast.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String accessTokenExpiry;
    private String refreshTokenExpiry;
    private UserDto user;
}
