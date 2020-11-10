package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName OrderApplication
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/11/10 0010
 * @Version V1.0
 **/
@SpringBootApplication
@EnableFeignClients
@MapperScan(basePackages = {"com.changgou.order.dao"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
