package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName Eureka
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/5/18 0018
 * @Version V1.0
 **/

@SpringBootApplication
//开启eureka服务
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
    }
}
