package moe.prashast.dto;

import lombok.Data;

@Data
public class SpecialEducatorDto {
    private Long seSchoolConfigurationId;
    private Long SpecialEducatorId;
    private Integer schoolId;
    private Short yearId;
    private Short languageId;
    private Short moduleId;
    private Integer[] moduleIds;
    private Integer[] roleIds;
    private String trainingSkillFlags;
    private Short trainingSkillCount;
    private Short isActive;
}
