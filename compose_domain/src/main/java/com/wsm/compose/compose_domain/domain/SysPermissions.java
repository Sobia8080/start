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
@TableName("sys_permissions")
public class SysPermissions {

    private long id;
    private String permission;
    private String description;
    private long rid;
    private long available;

}
