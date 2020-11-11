package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

/**
 * @ClassName CartService
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/11/10 0010
 * @Version V1.0
 **/
public interface CartService {
    /**
     * 添加购物车
     * @param id  sku的ID
     * @param num 购买的数量
     * @param username  购买的商品的用户名
     */
    void add(Long id, Integer num, String username);


    List<OrderItem> list(String username);
}
