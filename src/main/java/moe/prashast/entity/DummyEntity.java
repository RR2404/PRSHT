package moe.prashast.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@NamedNativeQueries({

        @NamedNativeQuery(
                name = "DummyEntity.getSummary",
                query =
                        "SELECT class_id as classId, " +
                                "       section_details as sectionDetails, " +
                                "       total_enr as totalEnr, " +
                                "       exec_status as execStatus, " +
                                "       error_description as errorDescription " +
                                "FROM public.get_school_class_section_summary(:yearId, :userId, :roleId, :stateId, :schoolId)",
                resultSetMapping = "SummaryMapping"
        ),

        @NamedNativeQuery(
                name = "DummyEntity.getDetails",
                query =
                        "SELECT class_id as classId, " +
                                "       section_details as sectionDetails, " +
                                "       total_enr as totalEnr, " +
                                "       exec_status as execStatus, " +
                                "       error_description as errorDescription " +
                                "FROM public.get_school_class_section_details(:yearId, :userId, :roleId, :stateId, :schoolId)",
                resultSetMapping = "DetailsMapping"
        )
})
@SqlResultSetMappings({

        @SqlResultSetMapping(
                name = "SummaryMapping",
                classes = @ConstructorResult(
                        targetClass = moe.prashast.dto.SchoolClassSectionSummaryDto.class,
                        columns = {
                                @ColumnResult(name = "classId", type = Integer.class),
                                @ColumnResult(name = "sectionDetails", type = String.class),
                                @ColumnResult(name = "totalEnr", type = Integer.class),
                                @ColumnResult(name = "execStatus", type = Integer.class),
                                @ColumnResult(name = "errorDescription", type = String.class)
                        }
                )
        ),

        @SqlResultSetMapping(
                name = "DetailsMapping",
                classes = @ConstructorResult(
                        targetClass = moe.prashast.dto.SchoolClassSectionDetailsDto.class,
                        columns = {
                                @ColumnResult(name = "classId", type = Integer.class),
                                @ColumnResult(name = "sectionDetails", type = String.class),
                                @ColumnResult(name = "totalEnr", type = Integer.class),
                                @ColumnResult(name = "execStatus", type = Integer.class),
                                @ColumnResult(name = "errorDescription", type = String.class)
                        }
                )
        )
})
public class DummyEntity {

    @Id
    private Long id;
}
