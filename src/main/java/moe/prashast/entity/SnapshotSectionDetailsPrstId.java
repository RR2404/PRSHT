package moe.prashast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SnapshotSectionDetailsPrstId implements Serializable {

    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @Column(name = "class_id", nullable = false)
    private Short classId;

    @Column(name = "section_id", nullable = false)
    private Short sectionId;

    @Column(name = "year_id", nullable = false)
    private Short yearId;
}

