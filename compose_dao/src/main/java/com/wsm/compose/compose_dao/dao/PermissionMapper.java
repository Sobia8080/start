package com.wsm.compose.compose_dao.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsm.compose.compose_domain.domain.SysPermissions;

import java.util.List;

/**
 * @name: PermissionMapper
 * @Author: wangshimin
 * @Date: 2019/11/14  14:41
 * @Description:
 */
public interface PermissionMapper extends BaseMapper<SysPermissions> {
    List<SysPermissions> selectPermissionsByUserId(long userId);
}
