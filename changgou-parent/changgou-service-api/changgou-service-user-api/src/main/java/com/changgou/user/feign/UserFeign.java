package com.changgou.user.feign;

import com.changgou.entity.Result;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName UserFeign
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/11/9 0009
 * @Version V1.0
 **/
@RequestMapping("/user")
@FeignClient("user")
public interface UserFeign {
    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
    @GetMapping("/load/{id}")
    Result<User> findById(@PathVariable String id);

}
