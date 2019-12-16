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
@TableName("sys_users")
public class SysUsers implements Serializable {

  private long id;
  private String username;
  private String password;
  private String salt;
  private String roleId;
  private long locked;

}
