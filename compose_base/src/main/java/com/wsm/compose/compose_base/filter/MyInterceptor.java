package com.wsm.compose.compose_base.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @name: MyInterceptor
 * @Author: wangshimin
 * @Date: 2019/11/15  15:14
 * @Description:
 */

public class MyInterceptor implements HandlerInterceptor {

    //请求前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //相关处理
        return true;
    }

    //请求后调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //相关处理...
    }

    //请求调用完成后回调方法，即在视图渲染完成后回调
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //相关处理...
    }
}
