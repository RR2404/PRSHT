package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(SchoolClassSectionId.class)
@NamedStoredProcedureQuery(
        name = "SchoolClassSectionDetails.fetchDetails",
        procedureName = "get_school_class_section_details",
        resultClasses = SchoolClassSectionDetailsEntity.class,
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_role_id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_state_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_school_id", type = Integer.class)
        }
)
public class  SchoolClassSectionDetailsEntity {

    @Id
    @Column(name = "class_id")
    private Integer classId;

    @Id
    @Column(name = "section_details")
    private String sectionDetails;

    @Column(name = "total_enr")
    private Integer totalEnr;

    @Column(name = "exec_status")
    private Integer execStatus;

    @Column(name = "error_description")
    private String errorDescription;
}
