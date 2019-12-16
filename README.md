





##### Complie

 介绍springBoot+mybatisplus+flyway+swagger2分模块父子工程demo

##### 软件架构

该项目采用springBoot工程

MyBatisPlus持久层框架 

flyway数据库版本统一管理

shiro权限控制    

swagger2接口管理

##### 实现功能

flyway数据库版本控制

shiro权限登录验证

swagger2接口管理

统一异常处理

XSS,SQL攻击拦截

AOP方法调用日志

自动代码生成（从实体类到接口，简单的增删改查代码）

##### 使用方式

​		克隆代码

​			git clone https://gitee.com/wangshiminhome/compose.git

​		编译代码

​			cd compose

​			mvn clean package -Dmaven.skip.test=true

​		运行程序

​			cd compose_api/target

​			java -jar compose_api-1.0-SNAPSHOT.jar

###### 模块介绍

​	compose_enum

​			枚举类包

​	compose_domain

​			实体类包

​	compose_dao

​			持久层包

​	compose_service

​			服务层包

​	compose_business

​			业务层包

​	compose_api

​			接口层包

​	compose_base

​			基础配置包

​	compose_shiro

​			shiro服务包

​	compose_bean

​			参数实体包

​	compose_util

​			工具类包			