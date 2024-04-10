package com.shuking.userService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.shuking")
@MapperScan("com.shuking.userService.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.shuking.service")
public class OjBackEndUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OjBackEndUserServiceApplication.class, args);
    }
}