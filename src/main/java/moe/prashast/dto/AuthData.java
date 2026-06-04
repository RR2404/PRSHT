package moe.prashast.dto;

import lombok.Data;

@Data
public class AuthData {
    private String userName;
    private String sek;
    private String authToken;
    private String authTokenExpiryTime;

}
