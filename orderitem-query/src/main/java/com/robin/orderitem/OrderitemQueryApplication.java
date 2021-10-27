package com.robin.orderitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.robin.orderitem.dao")
public class OrderitemQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderitemQueryApplication.class, args);
    }

}
