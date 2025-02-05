package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CodeApplication8081 {

    public static void main(String[] args) {
        SpringApplication.run(CodeApplication8081.class, args);
    }
}
