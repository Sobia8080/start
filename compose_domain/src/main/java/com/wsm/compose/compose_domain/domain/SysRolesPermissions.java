package com.wsm.compose.compose_domain.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_roles_permissions")
public class SysRolesPermissions {

    private long id;
    private long roleId;
    private long permissionId;

}
