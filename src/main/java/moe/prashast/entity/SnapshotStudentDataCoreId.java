package moe.prashast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SnapshotStudentDataCoreId implements Serializable {

    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "year_id", nullable = false)
    private Integer yearId;
}

