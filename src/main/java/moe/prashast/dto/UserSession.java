package moe.prashast.dto;

import lombok.Data;

@Data
public class UserSession {
    private String mobile;
    private String fullName;
    private String udiseCode;
    private String role;
    private Integer status;
}
