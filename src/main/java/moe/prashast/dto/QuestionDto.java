package moe.prashast.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private Integer questionId;
    private String questionText;
    private String answerType;
    private List<OptionDto> options;
}
