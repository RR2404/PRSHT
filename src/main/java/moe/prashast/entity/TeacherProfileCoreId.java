package moe.prashast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class TeacherProfileCoreId implements Serializable {

    @Column(name = "emp_staff_id", nullable = false)
    private Long empStaffId;

    @Column(name = "year_id", nullable = false)
    private Short yearId;
}
