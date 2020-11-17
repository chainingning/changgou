package com.changgou.oauth.interceptor;

import com.changgou.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName TokenRequestInterceptor
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/11/11 0011
 * @Version V1.0
 **/
@Configuration
public class TokenRequestInterceptor implements RequestInterceptor {

    /**
     * Feign执行之前拦截
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        /**
         * 从数据库查询加载用户信息
         * 1.没有令牌，Feign调用之前，生成令牌(admin)
         * 2.Feign调用之前，令牌需要携带过去
         * 3.Feign调用之前，令牌需要存放到header文件中
         * 4.请求-》Feign调用-》requestInterceptor->Feign调用之前拦截
         */

        //生成admin令牌
        String token = AdminToken.adminToken();
        requestTemplate.header("Authorization","bearer "+token);
    }
}
