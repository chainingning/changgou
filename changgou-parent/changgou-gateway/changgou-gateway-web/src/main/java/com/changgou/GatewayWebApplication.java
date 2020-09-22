package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName GatewayWebApplication
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/9/22 0022
 * @Version V1.0
 **/
@EnableEurekaClient
@SpringBootApplication
public class GatewayWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class,args);
    }
}
