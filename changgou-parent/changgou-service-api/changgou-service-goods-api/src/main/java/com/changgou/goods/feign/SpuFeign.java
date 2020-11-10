package com.changgou.goods.feign;

import com.changgou.entity.Result;
import com.changgou.goods.pojo.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName SpuFeign
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/11/10 0010
 * @Version V1.0
 **/
@FeignClient(value = "goods")
@RequestMapping("/spu")
public interface SpuFeign {
    /***
     * 根据ID查询Spu数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Spu> findById(@PathVariable Long id);
}
