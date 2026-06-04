package moe.prashast.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserRoleId implements Serializable {

    private Long user;
    private Short role;
}
