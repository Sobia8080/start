package com.wsm.compose.compose_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsm.compose.compose_service.service.UserSerice;
import com.wsm.compose.compose_dao.dao.PermissionMapper;
import com.wsm.compose.compose_dao.dao.RoleMapper;
import com.wsm.compose.compose_dao.dao.UserMapper;
import com.wsm.compose.compose_domain.domain.SysPermissions;
import com.wsm.compose.compose_domain.domain.SysRoles;
import com.wsm.compose.compose_domain.domain.SysUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @name: BookSericeImpl
 * @Author: wangshimin
 * @Date: 2019/11/12  14:23
 * @Description:
 */
@Service
public class UserSericeImpl extends ServiceImpl<UserMapper, SysUsers> implements UserSerice {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;


    @Override
    public SysUsers login(String name) {
        return userMapper.loginUser(name);
    }

    @Override
    public List<SysRoles> selectRoleByUser(long userId) {
        return roleMapper.selectRoleByUser(userId);
    }

    @Override
    public List<SysPermissions> selectPermissionsByUser(long userId) {
        return permissionMapper.selectPermissionsByUserId(userId);
    }
}
