package com.wsm.compose.compose_base.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @name: DatabaseConfig
 * @Author: wangshimin
 * @Date: 2019/11/13  9:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class DatabaseConfig {
    @Value(value = "${spring.datasource.url}")
    private String URL;
    @Value(value = "${spring.datasource.driver-class-name}")
    private String DRIVER;
    @Value(value = "${spring.datasource.username}")
    private String USERNAME;
    @Value(value = "${spring.datasource.password}")
    private String PASSWORD;
}
