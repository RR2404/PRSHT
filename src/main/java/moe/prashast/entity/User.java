package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "user_master")
@ToString(exclude = "userRole")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @Column(name = "udise_sch_code", length = 11)
    private String udiseSchCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_mobile", length = 15)
    private String phoneMobile;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "is_approved")
    private Short isApproved;

    @Column(name = "is_active")
    private Short isActive;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<UserRoleMap> userRole;

    @Column(name = "state_id")
    private Short stateId;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name="created_by")
    private String createdBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "hierarchy_id")
    private Long hierarchyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hierarchy_id",referencedColumnName = "hierarchy_id",insertable = false,updatable = false)
    private HierarchyMaster hierarchyMaster;

    @Column(name = "parent_id")
    private Long parentId;

}
