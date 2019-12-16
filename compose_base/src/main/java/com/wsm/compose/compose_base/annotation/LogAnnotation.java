package com.wsm.compose.compose_base.annotation;

import java.lang.annotation.*;

/**
 * @name: MyAnnotation
 * @Author: wangshimin
 * @Date: 2019/11/15  11:32
 * @Description:自定义扫描注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation {

}
