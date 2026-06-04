package moe.prashast.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "snapshot_section_details_prst")
public class SnapshotSectionDetailsPrst {

    @EmbeddedId
    private SnapshotSectionDetailsPrstId id;

    @Column(name = "section_name", length = 30)
    private String sectionName;

    @Column(name = "section_alias", length = 30)
    private String sectionAlias;

    @Column(name = "enr_total", nullable = false)
    private Short enrTotal = 0;

    @Column(name = "enr_boys", nullable = false)
    private Short enrBoys = 0;

    @Column(name = "enr_girls", nullable = false)
    private Short enrGirls = 0;

    @Column(name = "enr_tg", nullable = false)
    private Short enrTg = 0;

    @Column(name = "is_active")
    private Short isActive = 2;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}
