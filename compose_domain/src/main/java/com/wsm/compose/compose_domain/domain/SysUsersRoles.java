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
@TableName("sys_users_roles")
public class SysUsersRoles {

  private long id;
  private long userId;
  private long roleId;

}
