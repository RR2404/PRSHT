package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "login_session_mgmt_history", schema = "public")
@Data
public class LoginSessionMgmtHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="login_id", nullable = false)
    private Long loginId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "role_id", nullable = false)
    private Short roleId;

    @Column(name = "phone_mobile", length = 15)
    private String phoneMobile;

    @Column(name = "udise_code", length = 20)
    private String udiseCode;

    @Column(name = "access_token", length = 500)
    private String accessToken;

    @Column(name = "access_token_expiry")
    private String accessTokenExpiry;

    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    @Column(name = "refresh_token_expiry")
    private String refreshTokenExpiry;

    @Column(name = "ip_address", length = 255)
    private String ipAddress;

    @Column(name = "user_agent_browser_device", length = 255)
    private String userAgentBrowserDevice;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Column(name = "login_type", nullable = false)
    private Short loginType;
}