package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "role_module_permission")
@IdClass(RoleModulePermissionId.class)
public class RoleModulePermission {

    @Id
    @Column(name = "role_id")
    private Short roleId;

    @Id
    @Column(name = "module_id")
    private Short moduleId;

    @Column(name = "is_view", nullable = false)
    private Short isView = 2;

    @Column(name = "is_create", nullable = false)
    private Short isCreate = 2;

    @Column(name = "is_update", nullable = false)
    private Short isUpdate = 2;

    @Column(name = "is_verify", nullable = false)
    private Short isVerify = 2;

    @Column(name = "is_assign", nullable = false)
    private Short isAssign = 2;

    @Column(name = "is_approve", nullable = false)
    private Short isApprove = 2;

    @Column(name = "is_active", nullable = false)
    private Short isActive = 2;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @UpdateTimestamp
    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}
