package moe.prashast.dto;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class SpecialEducatorGetDto {

    @FieldValidation(
            fieldName = "Special Educator Id",
            required = true
    )
    private Long specialEducatorId;

    @FieldValidation(
            fieldName = "School Id",
            required = true
    )
    private Integer schoolId;

    @FieldValidation(
            fieldName = "Year Id",
            required = true
    )
    private Short yearId;
}
