package moe.prashast.dto;

import lombok.Data;

@Data
public class SchoolClassSectionDetailsDto {

    private Integer classId;
    private String sectionDetails;
    private Integer totalEnr;
    private Integer execStatus;
    private String errorDescription;
}
