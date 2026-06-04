package moe.prashast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mst_impairment_question_option", schema = "master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MstImpairmentQuestionOption {

    @Id
    @Column(name = "option_id", nullable = false)
    private Integer optionId;

    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Column(name = "language_id", nullable = false)
    private Short languageId;

    @Column(name = "option_code", length = 5)
    private String optionCode;

    @Column(name = "option_text", nullable = false, columnDefinition = "text")
    private String optionText;

    @Column(name = "display_order")
    private Short displayOrder;

    @Column(name = "is_active")
    private Short isActive;

    @Column(name = "created_by", length = 30)
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_by", length = 30)
    private String modifiedBy;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;
}
