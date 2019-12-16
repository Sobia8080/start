package com.wsm.compose.compose_shiro.api;

import com.wsm.compose.compose_base.annotation.LogAnnotation;
import com.wsm.compose.compose_bean.request.LoginRequest;
import com.wsm.compose.compose_util.core.MyException;
import com.wsm.compose.compose_util.core.Result;
import com.wsm.compose.compose_util.core.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: LoginApi
 * @Author: wangshimin
 * @Date: 2019/11/13  14:27
 * @Description:
 */
@Api(tags = {"Shiro登录管理"})
@RestController
public class LoginApi {

    @LogAnnotation
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(LoginRequest login) {
        ValidatorUtil.validateEntity(login);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(login.getUserName(), login.getPassword());
        try {
            subject.login(token);
            token.setRememberMe(true);
        } catch (UnknownAccountException e) {
            throw new MyException(0, "账户名不存在");
        } catch (IncorrectCredentialsException e) {
            throw new MyException(0, "密码不正确");
        } catch (LockedAccountException e) {
            throw new MyException(0, "账户被锁定");
        }
        return Result.success();
    }


    @LogAnnotation
    @ApiOperation(value = "没有权限跳转地址")
    @RequestMapping(value = "/unauthc", method = RequestMethod.GET)
    public void unAuthc() {
        throw new MyException(0, "没有权限");
    }


    @LogAnnotation
    @ApiOperation(value = "未登录跳转地址")
    @RequestMapping(value = "/getLogin", method = RequestMethod.GET)
    public void getLogin() {
        throw new MyException(0, "请先登录");
    }

    @LogAnnotation
    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success();
    }
}
