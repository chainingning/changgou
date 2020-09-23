package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    /**
     * 创建用户唯一标识，使用IP作为用户唯一标识，根据IP进行限流操作
     */
    @Bean(name = "ipKeyResolver")
    public KeyResolver userKeyResolver(){
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                String hostString = exchange.getRequest().getRemoteAddress().getHostString();
                return Mono.just(hostString);
            }
        };
    }
}
