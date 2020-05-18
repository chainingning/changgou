package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName GoodsApplication
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/5/18 0018
 * @Version V1.0
 **/
@SpringBootApplication
@EnableEurekaClient
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class);
    }
}
