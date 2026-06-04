package moe.prashast.dto;

import lombok.Data;

@Data
public class SchoolClassSectionSummaryDto {

    private Integer classId;
    private String sectionDetails;
    private Integer totalEnr;
    private Integer execStatus;
    private String errorDescription;
}
