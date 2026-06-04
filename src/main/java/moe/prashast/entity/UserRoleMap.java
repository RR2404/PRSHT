package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import moe.prashast.dto.UserSession;
import org.springframework.context.annotation.EnableMBeanExport;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_role_map")
@ToString(exclude = {"user", "role"})
@IdClass(UserRoleId.class)
public class UserRoleMap {

    @Id
//    @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Id
//    @Column(name = "role_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",nullable = false)
    private RoleMaster role;

    @Column(name = "is_active")
    private Short isActive;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name="modified_by")
    private String modifiedBy;

    @Column(name = "role_entity_id")
    private Long roleEntityId;
}
