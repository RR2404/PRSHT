package moe.prashast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialEducatorPrstId implements Serializable {

    @Column(name = "emp_staff_id")
    private Long empStaffId;

    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "year_id")
    private Short yearId;
}
