package com.wsm.compose.compose_shiro.realm;

import com.wsm.compose.compose_domain.domain.SysPermissions;
import com.wsm.compose.compose_domain.domain.SysRoles;
import com.wsm.compose.compose_domain.domain.SysUsers;
import com.wsm.compose.compose_service.service.UserSerice;
import com.wsm.compose.compose_util.core.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    UserSerice userSerice;


    /**
     * 配置权限 注入权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUsers user = (SysUsers) principals.getPrimaryPrincipal();
        try {
            //注入角色(查询所有的角色注入控制器）
            List<SysRoles> list = userSerice.selectRoleByUser(user.getId());
            for (SysRoles role : list) {
                authorizationInfo.addRole(role.getRole());
            }
            //注入角色所有权限（查询用户所有的权限注入控制器）
            List<SysPermissions> sysAuths = userSerice.selectPermissionsByUser(user.getId());
            for (SysPermissions sysAuth : sysAuths) {
                authorizationInfo.addStringPermission(sysAuth.getPermission());
            }
        } catch (Exception e) {
            log.error("获取角色失败");
            throw new MyException(0, "获取角色失败");
        }
        return authorizationInfo;
    }

    /**
     * 用户验证
     *
     * @param token 账户数据
     * @return
     * @throws AuthenticationException 根据账户数据查询账户。根据账户状态抛出对应的异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的名称
        String username = (String) token.getPrincipal();
        //这里需注意。看别人的教程有人是这样写的String password = (String) token.getCredentials();
        //项目运行的时候报错，发现密码不正确。后来进源码查看发现将密码注入后。Shiro会进行转义将字符串转换成字符数组。
        //源码：this(username, password != null ? password.toCharArray() : null, false, null);
        String password = new String((char[]) token.getCredentials());
        //通过username从数据库中查找 User对象，
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUsers user = userSerice.login(username);
        if (null == user) {
            throw new UnknownAccountException();
        } else {
            if (password.equals(user.getPassword())) {
                if (user.getLocked() == 0) {
                    SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
                    return authorizationInfo;
                } else {
                    throw new LockedAccountException();
                }
            } else {
                throw new IncorrectCredentialsException();
            }
        }
    }
}