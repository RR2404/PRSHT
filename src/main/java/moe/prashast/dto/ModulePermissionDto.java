package moe.prashast.dto;

import lombok.Data;

@Data
public class ModulePermissionDto {

    private Short is_view;
    private Short is_create;
    private Short is_update;
    private Short is_verify;
    private Short is_assign;
    private Short is_approve;
}
