package moe.prashast.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherProfilePrstId implements Serializable {

    private Long empStaffId;
    private Short yearId;
}
