package moe.prashast.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class SchoolClassSectionId implements Serializable {

    private Integer classId;
    private String sectionDetails;
}
