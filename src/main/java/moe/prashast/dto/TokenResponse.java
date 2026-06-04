package moe.prashast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private Date expiryTime;
}
