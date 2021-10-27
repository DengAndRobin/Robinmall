package com.robin.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.robin.stock.dao")
public class StockQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockQueryApplication.class, args);
    }

}
