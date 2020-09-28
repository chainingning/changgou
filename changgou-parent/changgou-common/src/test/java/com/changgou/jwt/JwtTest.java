package com.changgou.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName JwtTest
 * @Description: 令牌生成和解析
 * @Author ning.chai@foxmail.com
 * @Date 2020/9/27 0027
 * @Version V1.0
 **/
public class JwtTest {
    @Test
    public void testCreateToken(){
        JwtBuilder builder = Jwts.builder();
        builder.setIssuer("changgou") //颁发者
                .setIssuedAt(new Date())//颁发时间
                .setSubject("JWT令牌测试")//主题信息
                .setExpiration(new Date(System.currentTimeMillis()+360000))//过期时间
                .signWith(SignatureAlgorithm.HS256,"chaining");//设置签名 使用HS256算法，并设置SecretKey(字符串)

        //自定义载荷信息
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("company","sc");
        userInfo.put("addr","无锡");

        builder.addClaims(userInfo);
        //构建 并返回一个字符串
        System.out.println( builder.compact() );
    }


    /***
     * 解析Jwt令牌数据
     */
    @Test
    public void testParseJwt(){
        String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjaGFuZ2dvdSIsImlhdCI6MTYwMTE5MTUxNywic3ViIjoiSldU5Luk54mM5rWL6K-VIn0.8PUw9Ox5hkh7fzxSF1J53VqDy8VJKPhY5G13NM3oSGI";
        Claims claims = Jwts.parser().
                setSigningKey("chaining").
                parseClaimsJws(compactJwt).
        getBody();
        System.out.println(claims);
    }
}
