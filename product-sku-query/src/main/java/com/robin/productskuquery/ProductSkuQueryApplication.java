package com.robin.productskuquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.robin.productskuquery.dao")
public class ProductSkuQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductSkuQueryApplication.class, args);
    }

}
