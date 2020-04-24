package com.shopping.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.lt.mapper")
public class ShoppingContentApp {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingContentApp.class);
    }

}
