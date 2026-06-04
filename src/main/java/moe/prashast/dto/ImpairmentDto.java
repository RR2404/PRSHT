package moe.prashast.dto;

import lombok.Data;

import java.util.List;

@Data
public class ImpairmentDto {
    private Integer impairmentId;
    private String impairmentName;
    private List<QuestionDto> questions;
}
