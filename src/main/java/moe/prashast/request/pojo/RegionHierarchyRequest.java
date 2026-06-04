package moe.prashast.request.pojo;

import lombok.Data;
import moe.prashast.validation.FieldValidation;

@Data
public class RegionHierarchyRequest {

    @FieldValidation(
            fieldName = "Hierarchy Level",
            required = true
    )
    private Long hierarchyLevel;

    @FieldValidation(
            fieldName = "Parent Id",
            required = true
    )
    private Long parentId;
}

