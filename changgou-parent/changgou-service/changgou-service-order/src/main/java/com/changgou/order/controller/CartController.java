package com.changgou.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CartController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/11/10 0010
 * @Version V1.0
 **/
@RequestMapping("/cart")
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public Result<List<OrderItem>> list(){
        //获取登录用户名
        String username = "changgou";
        List<OrderItem> list = cartService.list(username);
        return new Result<List<OrderItem>>(true,StatusCode.OK,"购物车列表查询成功",list);
    }


    @GetMapping(value = "/add")
    public Result add(Integer num,Long id){
        cartService.add(id,num,"changgou");
        return new Result(true, StatusCode.OK,"添加成功");
    }
}
