package moe.prashast.dto;

import lombok.Data;

@Data
public class SchoolSettingsDto {

    private String schoolId;
    private String schoolName;
    private Integer status;
    private String preferredLanguage;
    private String role;
    private String additionalRole;
    private Integer noOfDays;
}
