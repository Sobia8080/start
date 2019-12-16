package com.wsm.compose.compose_api.api;

import com.wsm.compose.compose_base.annotation.LogAnnotation;
import com.wsm.compose.compose_util.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: BookApi
 * @Author: wangshimin
 * @Date: 2019/11/12  14:24
 * @Description:
 */
@Api(tags = {"测试地址"})
@RestController
@RequestMapping("/demo")
public class DemoApi {

    @ApiOperation(value = "测试方法")
    @RequiresRoles("user")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @LogAnnotation
    public Result test() {
        return Result.success("测试成功");
    }
}
