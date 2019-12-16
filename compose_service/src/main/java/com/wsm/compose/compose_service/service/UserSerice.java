package com.wsm.compose.compose_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsm.compose.compose_domain.domain.SysPermissions;
import com.wsm.compose.compose_domain.domain.SysRoles;
import com.wsm.compose.compose_domain.domain.SysUsers;

import java.util.List;

/**
 * @name: BookSerice
 * @Author: wangshimin
 * @Date: 2019/11/12  14:22
 * @Description:
 */
public interface UserSerice extends IService<SysUsers> {

    SysUsers login(String name);

    List<SysRoles> selectRoleByUser(long userId);


    List<SysPermissions> selectPermissionsByUser(long userId);
}