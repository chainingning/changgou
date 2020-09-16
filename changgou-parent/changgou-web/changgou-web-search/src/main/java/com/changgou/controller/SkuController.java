package com.changgou.controller;


import com.changgou.search.feign.SkuFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @ClassName SkuController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/9/15 0015
 * @Version V1.0
 **/
@Controller//因为需要跳转页面，所以不能用restController
@RequestMapping(value = "/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    @GetMapping(value = "/list")
    public String search(@RequestParam(required = false) Map<String,String> searchMap, Model model){
        //调用changgou-service-search微服务
        Map search = skuFeign.search(searchMap);
        model.addAttribute("result",search);

        //将条件存储，用于页面条件回显
        model.addAttribute("searchMap",searchMap);
        return "search";
    }

    /**
     * 拼接组装用户请求的地址
     * 获取用户每次请求的地址
     * 页面需要在这次请求的地址上面添加额外的搜索条件
     */
    public String url(Map<String,String> searchMap){
        String url = "/search/list";
        return  "";
    }

}
