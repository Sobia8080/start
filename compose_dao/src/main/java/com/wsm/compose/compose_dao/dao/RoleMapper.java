package com.wsm.compose.compose_dao.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsm.compose.compose_domain.domain.SysRoles;

import java.util.List;

/**
 * @name: RoleMappper
 * @Author: wangshimin
 * @Date: 2019/11/14  14:33
 * @Description:
 */
public interface RoleMapper extends BaseMapper<SysRoles> {
    List<SysRoles> selectRoleByUser(long userId);
}
