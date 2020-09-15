package com.changgou.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @ClassName SkuFeign
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/9/15 0015
 * @Version V1.0
 **/
@FeignClient(name="search")
@RequestMapping("/search")
public interface SkuFeign {
    @GetMapping
    Map search(@RequestParam(required = false) Map searchMap);
}
