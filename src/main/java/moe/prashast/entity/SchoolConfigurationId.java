package moe.prashast.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SchoolConfigurationId implements Serializable {

    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "year_id")
    private Short yearId;
}