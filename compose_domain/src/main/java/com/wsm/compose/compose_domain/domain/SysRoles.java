package com.wsm.compose.compose_domain.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_roles")
public class SysRoles implements Serializable {

    private long id;
    private String role;
    private String description;
    private long pid;
    private long available;

}
