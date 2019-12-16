package com.wsm.compose.compose_dao.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsm.compose.compose_domain.domain.SysUsers;

/**
 * @name: UserMapper
 * @Author: wangshimin
 * @Date: 2019/11/14  9:55
 * @Description:
 */
public interface UserMapper extends BaseMapper<SysUsers> {

    SysUsers loginUser(String userName);
}
