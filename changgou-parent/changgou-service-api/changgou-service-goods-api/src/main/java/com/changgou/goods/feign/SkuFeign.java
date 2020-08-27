package com.changgou.goods.feign;

import com.changgou.entity.Result;
import com.changgou.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
//调用哪个微服务？看goods微服务的application中name属性
@FeignClient(value = "goods")
@RequestMapping("/sku")
public interface SkuFeign {
    @GetMapping
    Result<List<Sku>> findAll();
}
