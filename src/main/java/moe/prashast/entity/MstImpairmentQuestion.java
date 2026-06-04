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
@Table(name = "mst_screening_impairment_question", schema = "master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MstImpairmentQuestion {

    @Id
    @Column(name = "impairment_question_id", nullable = false)
    private Integer impairmentQuestionId;

    @Column(name = "year_id", nullable = false)
    private Short yearId;

    @Column(name = "language_id", nullable = false)
    private Short languageId;

    @Column(name = "impairment_id", nullable = false)
    private Short impairmentId;

    @Column(name = "section_id", nullable = false)
    private Short sectionId;

    @Column(name = "question_code", nullable = false, length = 10)
    private String questionCode;

    @Column(name = "question_order", nullable = false)
    private Short questionOrder;

    @Column(name = "question_text", nullable = false, columnDefinition = "text")
    private String questionText;

    @Column(name = "answer_type", nullable = false, length = 20)
    private String answerType;

    @Column(name = "is_options")
    private Short isOptions ;

    @Column(name = "is_mandatory")
    private Short isMandatory;

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
