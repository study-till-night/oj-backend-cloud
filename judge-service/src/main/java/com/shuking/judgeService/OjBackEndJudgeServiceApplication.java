package com.shuking.judgeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.shuking")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.shuking.service")
public class OjBackEndJudgeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OjBackEndJudgeServiceApplication.class, args);
    }
}