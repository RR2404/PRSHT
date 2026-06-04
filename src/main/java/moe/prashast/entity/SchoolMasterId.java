package moe.prashast.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolMasterId implements Serializable {

    private Integer schoolId;
    private Short yearId;
}