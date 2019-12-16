package com.wsm.compose.compose_api;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.wsm.compose.compose_util.core.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @name: ComplieApiApplication
 * @Author: wangshimin
 * @Date: 2019/11/12  11:17
 * @Description:
 */
@Slf4j
@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.wsm.compose.*"})
@MapperScan("com.wsm.compose.compose_dao")
public class ComposeApiApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(ComposeApiApplication.class, args);
        ConfigurableEnvironment environment = cac.getEnvironment();
        getMessage(environment);
    }

    /**
     *  mybatis-plus 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    private static void getMessage(Environment env) {
        String name = env.getProperty("spring.application.name");
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        String address = null;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            address = "127.0.0.1";
        }
        name = StringUtils.isEmpty(name) ? "" : name;
        port = StringUtils.isEmpty(port) ? "8080" : port;
        path = StringUtils.isEmpty(path) ? "" : path;

        log.info("\n" +
                        "》》》》》》》》》》》》》》》》》》》》》》\n" +
                        "{}启动成功\t\t\t\t\n" +
                        "测试地址\t\t\t  http://{}\t\n" +
                        "shiro登录地址\t\t  http://{}\t\n" +
                        "swagger2接口地址\t  http://{}\t\n" +
                        "》》》》》》》》》》》》》》》》》》》》》》\n"
                , name,
                address + ":" + port + path + "/" + "demo/test",
                address + ":" + port + path + "/" + "login",
                address + ":" + port + path + "/" + "swagger-ui.html");
    }
}
